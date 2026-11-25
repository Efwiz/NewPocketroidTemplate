package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import androidx.core.text.util.LocalePreferences;
import com.android.dex.DexFormat;
import com.gaurav.avnc.vnc.XKeySym;
import com.hivemq.client.mqtt.MqttClient;
import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.apk.PathMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.BroadcastMessageContainer;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenBounceOffScript;
import org.catrobat.catroid.content.WhenClonedWithNameScript;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.WhenMqttMessageScript;
import org.catrobat.catroid.content.WhenNotificationActionTriggeredScript;
import org.catrobat.catroid.content.WhenNotificationClickedScript;
import org.catrobat.catroid.content.bricks.AddEditBrick;
import org.catrobat.catroid.content.bricks.AddFileToApkBrick;
import org.catrobat.catroid.content.bricks.AddHingeBrick;
import org.catrobat.catroid.content.bricks.AddItemToUserListBrick;
import org.catrobat.catroid.content.bricks.AddRadioBrick;
import org.catrobat.catroid.content.bricks.AddTextToBufferBrick;
import org.catrobat.catroid.content.bricks.AddToBufferBrick;
import org.catrobat.catroid.content.bricks.Apply3dForceBrick;
import org.catrobat.catroid.content.bricks.ApplyAngularImpulseBrick;
import org.catrobat.catroid.content.bricks.ApplyBufferLookBrick;
import org.catrobat.catroid.content.bricks.ApplyForceBrick;
import org.catrobat.catroid.content.bricks.ApplyImpulseBrick;
import org.catrobat.catroid.content.bricks.ApplyShaderToImageBrick;
import org.catrobat.catroid.content.bricks.ApplyTorqueBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.AskGPTBrick;
import org.catrobat.catroid.content.bricks.AskGemini2Brick;
import org.catrobat.catroid.content.bricks.AskGeminiBrick;
import org.catrobat.catroid.content.bricks.AskSpeechBrick;
import org.catrobat.catroid.content.bricks.AssertEqualsBrick;
import org.catrobat.catroid.content.bricks.AssertUserListsBrick;
import org.catrobat.catroid.content.bricks.AsyncRepeatBrick;
import org.catrobat.catroid.content.bricks.AttachRaySensorBrick;
import org.catrobat.catroid.content.bricks.AttachSOBrick;
import org.catrobat.catroid.content.bricks.AttachToCameraBrick;
import org.catrobat.catroid.content.bricks.BackgroundRequestBrick;
import org.catrobat.catroid.content.bricks.BakeByPrefixBrick;
import org.catrobat.catroid.content.bricks.Base64ToFileBrick;
import org.catrobat.catroid.content.bricks.BigAskBrick;
import org.catrobat.catroid.content.bricks.BindVmOutputBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.BroadcastWaitBrick;
import org.catrobat.catroid.content.bricks.BroadcastWithParamsBrick;
import org.catrobat.catroid.content.bricks.BroadcastWithParamsReceiverBrick;
import org.catrobat.catroid.content.bricks.BufferMaskBrick;
import org.catrobat.catroid.content.bricks.CameraBrick;
import org.catrobat.catroid.content.bricks.CameraLookAtBrick;
import org.catrobat.catroid.content.bricks.CameraSettingsBrick;
import org.catrobat.catroid.content.bricks.CameraTouchControlBrick;
import org.catrobat.catroid.content.bricks.CameraTrackingBrick;
import org.catrobat.catroid.content.bricks.CastRayBrick;
import org.catrobat.catroid.content.bricks.ChangeBrightnessByNBrick;
import org.catrobat.catroid.content.bricks.ChangeColorByNBrick;
import org.catrobat.catroid.content.bricks.ChangeHeightBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeTempoByNBrick;
import org.catrobat.catroid.content.bricks.ChangeTransparencyByNBrick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.ChangeVolumeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeWidthBrick;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.ChooseCameraBrick;
import org.catrobat.catroid.content.bricks.ChooseFileBrick;
import org.catrobat.catroid.content.bricks.ClearBackgroundBrick;
import org.catrobat.catroid.content.bricks.ClearGraphicEffectBrick;
import org.catrobat.catroid.content.bricks.ClearPythonEnvironmentBrick;
import org.catrobat.catroid.content.bricks.ClearSceneBrick;
import org.catrobat.catroid.content.bricks.ClearUserListBrick;
import org.catrobat.catroid.content.bricks.CloneAndNameBrick;
import org.catrobat.catroid.content.bricks.CloneBrick;
import org.catrobat.catroid.content.bricks.CloneObjectBrick;
import org.catrobat.catroid.content.bricks.ComeToFrontBrick;
import org.catrobat.catroid.content.bricks.ConfigureLightBrick;
import org.catrobat.catroid.content.bricks.ConfigureMaterialBrick;
import org.catrobat.catroid.content.bricks.ConfigureParticlesBrick;
import org.catrobat.catroid.content.bricks.ConnectServerBrick;
import org.catrobat.catroid.content.bricks.CopyLookBrick;
import org.catrobat.catroid.content.bricks.CopyProjectFileBrick;
import org.catrobat.catroid.content.bricks.CopyTextBrick;
import org.catrobat.catroid.content.bricks.Create3dObjectBrick;
import org.catrobat.catroid.content.bricks.CreateBufferBrick;
import org.catrobat.catroid.content.bricks.CreateButtonBrick;
import org.catrobat.catroid.content.bricks.CreateCubeBrick;
import org.catrobat.catroid.content.bricks.CreateDialogBrick;
import org.catrobat.catroid.content.bricks.CreateDiskBrick;
import org.catrobat.catroid.content.bricks.CreateDistanceJointBrick;
import org.catrobat.catroid.content.bricks.CreateFloatBrick;
import org.catrobat.catroid.content.bricks.CreateGLViewBrick;
import org.catrobat.catroid.content.bricks.CreateGearJointBrick;
import org.catrobat.catroid.content.bricks.CreateImageViewBrick;
import org.catrobat.catroid.content.bricks.CreateParticlesBrick;
import org.catrobat.catroid.content.bricks.CreatePointJointBrick;
import org.catrobat.catroid.content.bricks.CreatePrismaticJointBrick;
import org.catrobat.catroid.content.bricks.CreatePulleyJointBrick;
import org.catrobat.catroid.content.bricks.CreateRevoluteJointBrick;
import org.catrobat.catroid.content.bricks.CreateScrollViewBrick;
import org.catrobat.catroid.content.bricks.CreateSliderBrick;
import org.catrobat.catroid.content.bricks.CreateSphereBrick;
import org.catrobat.catroid.content.bricks.CreateSwitchBrick;
import org.catrobat.catroid.content.bricks.CreateTableBrick;
import org.catrobat.catroid.content.bricks.CreateTextFieldBrick;
import org.catrobat.catroid.content.bricks.CreateTextLabelBrick;
import org.catrobat.catroid.content.bricks.CreateVarBrick;
import org.catrobat.catroid.content.bricks.CreateVideoBrick;
import org.catrobat.catroid.content.bricks.CreateWebFileBrick;
import org.catrobat.catroid.content.bricks.CreateWebUrlBrick;
import org.catrobat.catroid.content.bricks.CreateWeldJointBrick;
import org.catrobat.catroid.content.bricks.CustomBrick;
import org.catrobat.catroid.content.bricks.CutLookBrick;
import org.catrobat.catroid.content.bricks.DelSquareBrick;
import org.catrobat.catroid.content.bricks.DeleteAllTablesBrick;
import org.catrobat.catroid.content.bricks.DeleteBaseBrick;
import org.catrobat.catroid.content.bricks.DeleteFilesBrick;
import org.catrobat.catroid.content.bricks.DeleteFloatBrick;
import org.catrobat.catroid.content.bricks.DeleteFromApkBrick;
import org.catrobat.catroid.content.bricks.DeleteItemOfUserListBrick;
import org.catrobat.catroid.content.bricks.DeleteLookBrick;
import org.catrobat.catroid.content.bricks.DeleteParticlesBrick;
import org.catrobat.catroid.content.bricks.DeleteTableBrick;
import org.catrobat.catroid.content.bricks.DeleteThisCloneBrick;
import org.catrobat.catroid.content.bricks.DeleteVarBrick;
import org.catrobat.catroid.content.bricks.DeleteVarsBrick;
import org.catrobat.catroid.content.bricks.DeleteWebBrick;
import org.catrobat.catroid.content.bricks.DestroyJointBrick;
import org.catrobat.catroid.content.bricks.DetachFromCameraBrick;
import org.catrobat.catroid.content.bricks.DisableBackgroundModeBrick;
import org.catrobat.catroid.content.bricks.DroneEmergencyBrick;
import org.catrobat.catroid.content.bricks.DroneFlipBrick;
import org.catrobat.catroid.content.bricks.DroneMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.DroneMoveDownBrick;
import org.catrobat.catroid.content.bricks.DroneMoveForwardBrick;
import org.catrobat.catroid.content.bricks.DroneMoveLeftBrick;
import org.catrobat.catroid.content.bricks.DroneMoveRightBrick;
import org.catrobat.catroid.content.bricks.DroneMoveUpBrick;
import org.catrobat.catroid.content.bricks.DronePlayLedAnimationBrick;
import org.catrobat.catroid.content.bricks.DroneSwitchCameraBrick;
import org.catrobat.catroid.content.bricks.DroneTakeOffLandBrick;
import org.catrobat.catroid.content.bricks.DroneTurnLeftBrick;
import org.catrobat.catroid.content.bricks.DroneTurnRightBrick;
import org.catrobat.catroid.content.bricks.Ease3DPropertyBrick;
import org.catrobat.catroid.content.bricks.EasePropertyBrick;
import org.catrobat.catroid.content.bricks.EditLookBrick;
import org.catrobat.catroid.content.bricks.EnableBackgroundModeBrick;
import org.catrobat.catroid.content.bricks.EnablePbrRenderBrick;
import org.catrobat.catroid.content.bricks.EvalWebBrick;
import org.catrobat.catroid.content.bricks.ExitStageBrick;
import org.catrobat.catroid.content.bricks.Export3dObjectToGlbBrick;
import org.catrobat.catroid.content.bricks.ExportProjectFileBrick;
import org.catrobat.catroid.content.bricks.ExtractFileBrick;
import org.catrobat.catroid.content.bricks.FadeParticleEffectBrick;
import org.catrobat.catroid.content.bricks.Fast2DApplyForceBrick;
import org.catrobat.catroid.content.bricks.Fast2DApplyImpulseBrick;
import org.catrobat.catroid.content.bricks.Fast2DCreateBrick;
import org.catrobat.catroid.content.bricks.Fast2DDeleteBrick;
import org.catrobat.catroid.content.bricks.Fast2DMakePhysicsBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetAngularVelocityBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetCameraBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetCollisionFilterBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetColorBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetGravityBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetPhysicsVelocityBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetPositionBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetRotationBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetScaleBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetTextureBrick;
import org.catrobat.catroid.content.bricks.Fast2DSetVelocityBrick;
import org.catrobat.catroid.content.bricks.FileUrlBrick;
import org.catrobat.catroid.content.bricks.FilesUrlBrick;
import org.catrobat.catroid.content.bricks.FinishStageBrick;
import org.catrobat.catroid.content.bricks.FlashBrick;
import org.catrobat.catroid.content.bricks.ForItemInUserListBrick;
import org.catrobat.catroid.content.bricks.ForVariableFromToBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GenerateKeyBrick;
import org.catrobat.catroid.content.bricks.GetZipFileNamesBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.GoNStepsBackBrick;
import org.catrobat.catroid.content.bricks.GoToBrick;
import org.catrobat.catroid.content.bricks.GrayscaleImgBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.HideStatusBarBrick;
import org.catrobat.catroid.content.bricks.HideText3Brick;
import org.catrobat.catroid.content.bricks.HideTextBrick;
import org.catrobat.catroid.content.bricks.HttpAttachFileBrick;
import org.catrobat.catroid.content.bricks.HttpBodyBrick;
import org.catrobat.catroid.content.bricks.HttpConfigBrick;
import org.catrobat.catroid.content.bricks.HttpCreateBrick;
import org.catrobat.catroid.content.bricks.HttpSaveFileBrick;
import org.catrobat.catroid.content.bricks.HttpSendBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.InsertItemIntoUserListBrick;
import org.catrobat.catroid.content.bricks.InsertTableBrick;
import org.catrobat.catroid.content.bricks.InstantBrick;
import org.catrobat.catroid.content.bricks.IntervalRepeatBrick;
import org.catrobat.catroid.content.bricks.JavaCompileBrick;
import org.catrobat.catroid.content.bricks.JavaDownloadDepBrick;
import org.catrobat.catroid.content.bricks.JavaLoadAndRunBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoAnimationsBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoJumpHighBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoJumpLongBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoMoveForwardBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoNoSoundBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoRotateLeftBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoRotateRightBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoSoundBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoTakingPictureBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoTurnBrick;
import org.catrobat.catroid.content.bricks.KeyEventBrick;
import org.catrobat.catroid.content.bricks.KeyframeAnimationBrick;
import org.catrobat.catroid.content.bricks.LaunchProjectBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoEv3PlayToneBrick;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoNxtPlayToneBrick;
import org.catrobat.catroid.content.bricks.ListenMicroBrick;
import org.catrobat.catroid.content.bricks.ListenServerBrick;
import org.catrobat.catroid.content.bricks.LoadFromInternalStorageBrick;
import org.catrobat.catroid.content.bricks.LoadNNBrick;
import org.catrobat.catroid.content.bricks.LoadNativeModuleBrick;
import org.catrobat.catroid.content.bricks.LoadPythonLibraryBrick;
import org.catrobat.catroid.content.bricks.LoadSceneAdditiveBrick;
import org.catrobat.catroid.content.bricks.LoadSceneBrick;
import org.catrobat.catroid.content.bricks.LookFileBrick;
import org.catrobat.catroid.content.bricks.LookFromTableBrick;
import org.catrobat.catroid.content.bricks.LookRequestBrick;
import org.catrobat.catroid.content.bricks.LookToTableBrick;
import org.catrobat.catroid.content.bricks.LunoScriptBrick;
import org.catrobat.catroid.content.bricks.MLLoadBrick;
import org.catrobat.catroid.content.bricks.MLSaveBrick;
import org.catrobat.catroid.content.bricks.MLStepAdamBrick;
import org.catrobat.catroid.content.bricks.MouseEventBrick;
import org.catrobat.catroid.content.bricks.MoveDownloadsBrick;
import org.catrobat.catroid.content.bricks.MoveFilesBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.MqttConnectBrick;
import org.catrobat.catroid.content.bricks.MqttDisconnectBrick;
import org.catrobat.catroid.content.bricks.MqttJoinRoomBrick;
import org.catrobat.catroid.content.bricks.MqttPublishBrick;
import org.catrobat.catroid.content.bricks.NativeViewAnimateBrick;
import org.catrobat.catroid.content.bricks.NativeViewBindSpriteBrick;
import org.catrobat.catroid.content.bricks.NativeViewConfigBrick;
import org.catrobat.catroid.content.bricks.NativeViewControlBrick;
import org.catrobat.catroid.content.bricks.NativeViewListenerBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.NormalizeImgBrick;
import org.catrobat.catroid.content.bricks.NoteBrick;
import org.catrobat.catroid.content.bricks.NotificationAddButtonBrick;
import org.catrobat.catroid.content.bricks.NotificationCancelBrick;
import org.catrobat.catroid.content.bricks.NotificationCreateBrick;
import org.catrobat.catroid.content.bricks.NotificationShowBrick;
import org.catrobat.catroid.content.bricks.ObjectLookAtBrick;
import org.catrobat.catroid.content.bricks.ObjectLookAtCorrectBrick;
import org.catrobat.catroid.content.bricks.OpenAppBrick;
import org.catrobat.catroid.content.bricks.OpenFileBrick;
import org.catrobat.catroid.content.bricks.OpenUrlBrick;
import org.catrobat.catroid.content.bricks.OrientationBrick;
import org.catrobat.catroid.content.bricks.PaintNewLookBrick;
import org.catrobat.catroid.content.bricks.ParameterizedBrick;
import org.catrobat.catroid.content.bricks.ParameterizedEndBrick;
import org.catrobat.catroid.content.bricks.ParticleEffectAdditivityBrick;
import org.catrobat.catroid.content.bricks.PauseForBeatsBrick;
import org.catrobat.catroid.content.bricks.PauseVideoBrick;
import org.catrobat.catroid.content.bricks.PenDownBrick;
import org.catrobat.catroid.content.bricks.PenUpBrick;
import org.catrobat.catroid.content.bricks.PerformRayCastBrick;
import org.catrobat.catroid.content.bricks.PhiroIfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick;
import org.catrobat.catroid.content.bricks.PhotoBrick;
import org.catrobat.catroid.content.bricks.PinToCameraBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlayAnimationBrick;
import org.catrobat.catroid.content.bricks.PlayDrumForBeatsBrick;
import org.catrobat.catroid.content.bricks.PlayGifBrick;
import org.catrobat.catroid.content.bricks.PlayNoteForBeatsBrick;
import org.catrobat.catroid.content.bricks.PlayPreparedSoundBrick;
import org.catrobat.catroid.content.bricks.PlaySoundAndWaitBrick;
import org.catrobat.catroid.content.bricks.PlaySoundAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundAtPositionBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.PlaySpritesheetBrick;
import org.catrobat.catroid.content.bricks.PlayVideoBrick;
import org.catrobat.catroid.content.bricks.PointInDirectionBrick;
import org.catrobat.catroid.content.bricks.PointToBrick;
import org.catrobat.catroid.content.bricks.PostWebRequestBrick;
import org.catrobat.catroid.content.bricks.PredictNNBrick;
import org.catrobat.catroid.content.bricks.PrepareMusicAs3DSoundBrick;
import org.catrobat.catroid.content.bricks.PrepareSoundBrick;
import org.catrobat.catroid.content.bricks.PrepareSoundBrick2;
import org.catrobat.catroid.content.bricks.PreviousLookBrick;
import org.catrobat.catroid.content.bricks.PromoteLightBrick;
import org.catrobat.catroid.content.bricks.PtBackwardBrick;
import org.catrobat.catroid.content.bricks.PtCreateTensorBrick;
import org.catrobat.catroid.content.bricks.PtOpBrick;
import org.catrobat.catroid.content.bricks.PtReshapeBrick;
import org.catrobat.catroid.content.bricks.PtSetByIndexBrick;
import org.catrobat.catroid.content.bricks.PtSetTensorBrick;
import org.catrobat.catroid.content.bricks.PtSetTrainingBrick;
import org.catrobat.catroid.content.bricks.PtStepBrick;
import org.catrobat.catroid.content.bricks.PutFloatBrick;
import org.catrobat.catroid.content.bricks.RaspiIfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.RaspiPwmBrick;
import org.catrobat.catroid.content.bricks.RaspiSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.ReadBaseBrick;
import org.catrobat.catroid.content.bricks.ReadFromFilesBrick;
import org.catrobat.catroid.content.bricks.ReadListFromDeviceBrick;
import org.catrobat.catroid.content.bricks.ReadVariableFromDeviceBrick;
import org.catrobat.catroid.content.bricks.ReadVariableFromFileBrick;
import org.catrobat.catroid.content.bricks.RegexBrick;
import org.catrobat.catroid.content.bricks.Remove3dObjectBrick;
import org.catrobat.catroid.content.bricks.RemoveFromBufferBrick;
import org.catrobat.catroid.content.bricks.RemoveJointBrick;
import org.catrobat.catroid.content.bricks.RemoveObjectsByPrefixBrick;
import org.catrobat.catroid.content.bricks.RemoveParentBrick;
import org.catrobat.catroid.content.bricks.RemovePbrLightBrick;
import org.catrobat.catroid.content.bricks.RepeatBrick;
import org.catrobat.catroid.content.bricks.RepeatUntilBrick;
import org.catrobat.catroid.content.bricks.Replace3DModelBrick;
import org.catrobat.catroid.content.bricks.ReplaceItemInUserListBrick;
import org.catrobat.catroid.content.bricks.ResetTimerBrick;
import org.catrobat.catroid.content.bricks.ResizeImgBrick;
import org.catrobat.catroid.content.bricks.ReturnToPreviousProjectBrick;
import org.catrobat.catroid.content.bricks.RotateCameraByBrick;
import org.catrobat.catroid.content.bricks.RunAsSpriteBrick;
import org.catrobat.catroid.content.bricks.RunChip8Brick;
import org.catrobat.catroid.content.bricks.RunJSBrick;
import org.catrobat.catroid.content.bricks.RunLuaBrick;
import org.catrobat.catroid.content.bricks.RunPythonScriptBrick;
import org.catrobat.catroid.content.bricks.RunShellBrick;
import org.catrobat.catroid.content.bricks.RunVMBrick;
import org.catrobat.catroid.content.bricks.RunVm2Brick;
import org.catrobat.catroid.content.bricks.RunningStitchBrick;
import org.catrobat.catroid.content.bricks.SaveBufferBrick;
import org.catrobat.catroid.content.bricks.SaveLookBrick;
import org.catrobat.catroid.content.bricks.SaveLookFilesBrick;
import org.catrobat.catroid.content.bricks.SavePlotBrick;
import org.catrobat.catroid.content.bricks.SaveToInternalStorageBrick;
import org.catrobat.catroid.content.bricks.SayBubbleBrick;
import org.catrobat.catroid.content.bricks.SayForBubbleBrick;
import org.catrobat.catroid.content.bricks.SceneIdBrick;
import org.catrobat.catroid.content.bricks.SceneStartBrick;
import org.catrobat.catroid.content.bricks.SceneTransitionBrick;
import org.catrobat.catroid.content.bricks.ScreenShotBrick;
import org.catrobat.catroid.content.bricks.SeekVideoBrick;
import org.catrobat.catroid.content.bricks.SendServerBrick;
import org.catrobat.catroid.content.bricks.SendVmInputBrick;
import org.catrobat.catroid.content.bricks.Set3DSoundMaxDistanceBrick;
import org.catrobat.catroid.content.bricks.Set3DSoundPositionBrick;
import org.catrobat.catroid.content.bricks.Set3dFrictionBrick;
import org.catrobat.catroid.content.bricks.Set3dGravityBrick;
import org.catrobat.catroid.content.bricks.Set3dPositionBrick;
import org.catrobat.catroid.content.bricks.Set3dRotationBrick;
import org.catrobat.catroid.content.bricks.Set3dScaleBrick;
import org.catrobat.catroid.content.bricks.Set3dVelocityBrick;
import org.catrobat.catroid.content.bricks.SetAIBrick;
import org.catrobat.catroid.content.bricks.SetActiveBrick;
import org.catrobat.catroid.content.bricks.SetAmbientLightBrick;
import org.catrobat.catroid.content.bricks.SetAnimationSpeedBrick;
import org.catrobat.catroid.content.bricks.SetAnisotropicFilterBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundAndWaitBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexAndWaitBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundLightBrick;
import org.catrobat.catroid.content.bricks.SetBounceBrick;
import org.catrobat.catroid.content.bricks.SetBrightnessBrick;
import org.catrobat.catroid.content.bricks.SetBufferAutoUpdateBrick;
import org.catrobat.catroid.content.bricks.SetBufferCamera3DBrick;
import org.catrobat.catroid.content.bricks.SetBufferCameraBrick;
import org.catrobat.catroid.content.bricks.SetBufferEffectsBrick;
import org.catrobat.catroid.content.bricks.SetBufferModeBrick;
import org.catrobat.catroid.content.bricks.SetBufferOnlyBrick;
import org.catrobat.catroid.content.bricks.SetBufferShaderBrick;
import org.catrobat.catroid.content.bricks.SetBufferShaderUniformBrick;
import org.catrobat.catroid.content.bricks.SetCCDBrick;
import org.catrobat.catroid.content.bricks.SetCallbackBrick;
import org.catrobat.catroid.content.bricks.SetCameraFocusPointBrick;
import org.catrobat.catroid.content.bricks.SetCameraPosition2Brick;
import org.catrobat.catroid.content.bricks.SetCameraPositionBrick;
import org.catrobat.catroid.content.bricks.SetCameraRangeBrick;
import org.catrobat.catroid.content.bricks.SetCameraRotation2Brick;
import org.catrobat.catroid.content.bricks.SetCameraRotationBrick;
import org.catrobat.catroid.content.bricks.SetCameraZoomBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetCornerOffsetsBrick;
import org.catrobat.catroid.content.bricks.SetDampingBrick;
import org.catrobat.catroid.content.bricks.SetDirectionalLight2Brick;
import org.catrobat.catroid.content.bricks.SetDirectionalLightBrick;
import org.catrobat.catroid.content.bricks.SetEmissiveBrick;
import org.catrobat.catroid.content.bricks.SetFogBrick;
import org.catrobat.catroid.content.bricks.SetFpsBrick;
import org.catrobat.catroid.content.bricks.SetFreeCameraBrick;
import org.catrobat.catroid.content.bricks.SetFrictionBrick;
import org.catrobat.catroid.content.bricks.SetGeminiKeyBrick;
import org.catrobat.catroid.content.bricks.SetGlobalSoundVolumeBrick;
import org.catrobat.catroid.content.bricks.SetGravityBrick;
import org.catrobat.catroid.content.bricks.SetHeightBrick;
import org.catrobat.catroid.content.bricks.SetHingeMotorBrick;
import org.catrobat.catroid.content.bricks.SetHitboxBrick;
import org.catrobat.catroid.content.bricks.SetInstrumentBrick;
import org.catrobat.catroid.content.bricks.SetListeningLanguageBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetLookByIndexBrick;
import org.catrobat.catroid.content.bricks.SetLookFilesBrick;
import org.catrobat.catroid.content.bricks.SetMainRenderLoopsBrick;
import org.catrobat.catroid.content.bricks.SetMassBrick;
import org.catrobat.catroid.content.bricks.SetMaterialBrick;
import org.catrobat.catroid.content.bricks.SetMaxPointLightsBrick;
import org.catrobat.catroid.content.bricks.SetNativeParentBrick;
import org.catrobat.catroid.content.bricks.SetNegativeBrick;
import org.catrobat.catroid.content.bricks.SetNeutralBrick;
import org.catrobat.catroid.content.bricks.SetNfcTagBrick;
import org.catrobat.catroid.content.bricks.SetObjectColorBrick;
import org.catrobat.catroid.content.bricks.SetObjectShaderBrick;
import org.catrobat.catroid.content.bricks.SetObjectShaderUniformBrick;
import org.catrobat.catroid.content.bricks.SetObjectTextureBrick;
import org.catrobat.catroid.content.bricks.SetParentBrick;
import org.catrobat.catroid.content.bricks.SetParticleColorBrick;
import org.catrobat.catroid.content.bricks.SetParticleEmissionBrick;
import org.catrobat.catroid.content.bricks.SetPenColorBrick;
import org.catrobat.catroid.content.bricks.SetPenSizeBrick;
import org.catrobat.catroid.content.bricks.SetPhysicsObjectTypeBrick;
import org.catrobat.catroid.content.bricks.SetPhysicsStateBrick;
import org.catrobat.catroid.content.bricks.SetPointLightBrick;
import org.catrobat.catroid.content.bricks.SetPositiveBrick;
import org.catrobat.catroid.content.bricks.SetPostProcessingBrick;
import org.catrobat.catroid.content.bricks.SetPostProcessingNewBrick;
import org.catrobat.catroid.content.bricks.SetRenderResolutionBrick;
import org.catrobat.catroid.content.bricks.SetRestitutionBrick;
import org.catrobat.catroid.content.bricks.SetRotationLockBrick;
import org.catrobat.catroid.content.bricks.SetRotationStyleBrick;
import org.catrobat.catroid.content.bricks.SetSaveScenesBrick;
import org.catrobat.catroid.content.bricks.SetScreenShaderBrick;
import org.catrobat.catroid.content.bricks.SetShaderCodeBrick;
import org.catrobat.catroid.content.bricks.SetShaderUniformFloatBrick;
import org.catrobat.catroid.content.bricks.SetShaderUniformVec3Brick;
import org.catrobat.catroid.content.bricks.SetShadowQualityBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetSkyColorBrick;
import org.catrobat.catroid.content.bricks.SetSkyboxBrick;
import org.catrobat.catroid.content.bricks.SetSoundInstancePitchBrick;
import org.catrobat.catroid.content.bricks.SetSoundInstanceVolumeBrick;
import org.catrobat.catroid.content.bricks.SetSoundVolumeBrick;
import org.catrobat.catroid.content.bricks.SetSpawnInvisibleBrick;
import org.catrobat.catroid.content.bricks.SetSpotLightBrick;
import org.catrobat.catroid.content.bricks.SetStopSoundsBrick;
import org.catrobat.catroid.content.bricks.SetTempoBrick;
import org.catrobat.catroid.content.bricks.SetTextBufferOnlyBrick;
import org.catrobat.catroid.content.bricks.SetTextureTilingBrick;
import org.catrobat.catroid.content.bricks.SetThirdPersonCameraBrick;
import org.catrobat.catroid.content.bricks.SetThreadColorBrick;
import org.catrobat.catroid.content.bricks.SetTransparencyBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.SetVariableEasingBrick;
import org.catrobat.catroid.content.bricks.SetVelocityBrick;
import org.catrobat.catroid.content.bricks.SetViewPositionBrick;
import org.catrobat.catroid.content.bricks.SetVolumeToBrick;
import org.catrobat.catroid.content.bricks.SetWebBrick;
import org.catrobat.catroid.content.bricks.SetWidthBrick;
import org.catrobat.catroid.content.bricks.SetXBrick;
import org.catrobat.catroid.content.bricks.SetYBrick;
import org.catrobat.catroid.content.bricks.SewUpBrick;
import org.catrobat.catroid.content.bricks.ShareBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.content.bricks.ShowDialogBrick;
import org.catrobat.catroid.content.bricks.ShowNotificationBrick;
import org.catrobat.catroid.content.bricks.ShowText3Brick;
import org.catrobat.catroid.content.bricks.ShowTextBrick;
import org.catrobat.catroid.content.bricks.ShowTextColorSizeAlignmentBrick;
import org.catrobat.catroid.content.bricks.ShowTextFontBrick;
import org.catrobat.catroid.content.bricks.ShowTextRotationBrick;
import org.catrobat.catroid.content.bricks.ShowToastBlock;
import org.catrobat.catroid.content.bricks.ShowVarFontBrick;
import org.catrobat.catroid.content.bricks.SignApkBrick;
import org.catrobat.catroid.content.bricks.SoundFileBrick;
import org.catrobat.catroid.content.bricks.SoundFilesBrick;
import org.catrobat.catroid.content.bricks.SpawnThreadBrick;
import org.catrobat.catroid.content.bricks.SpeakAndWaitBrick;
import org.catrobat.catroid.content.bricks.SpeakBrick;
import org.catrobat.catroid.content.bricks.SplitBrick;
import org.catrobat.catroid.content.bricks.SquareBrick;
import org.catrobat.catroid.content.bricks.StampBrick;
import org.catrobat.catroid.content.bricks.StartBufferRecordingBrick;
import org.catrobat.catroid.content.bricks.StartListeningBrick;
import org.catrobat.catroid.content.bricks.StartPlotBrick;
import org.catrobat.catroid.content.bricks.StartRecordingBrick;
import org.catrobat.catroid.content.bricks.StartServerBrick;
import org.catrobat.catroid.content.bricks.StitchBrick;
import org.catrobat.catroid.content.bricks.StopAllSoundsBrick;
import org.catrobat.catroid.content.bricks.StopAnimationBrick;
import org.catrobat.catroid.content.bricks.StopBufferRecordingBrick;
import org.catrobat.catroid.content.bricks.StopGifBrick;
import org.catrobat.catroid.content.bricks.StopPlotBrick;
import org.catrobat.catroid.content.bricks.StopRecordingBrick;
import org.catrobat.catroid.content.bricks.StopRunningStitchBrick;
import org.catrobat.catroid.content.bricks.StopScriptBrick;
import org.catrobat.catroid.content.bricks.StopServerBrick;
import org.catrobat.catroid.content.bricks.StopSoundBrick;
import org.catrobat.catroid.content.bricks.StopSoundBrick2;
import org.catrobat.catroid.content.bricks.StopSpritesheetBrick;
import org.catrobat.catroid.content.bricks.StopThreadBrick;
import org.catrobat.catroid.content.bricks.StopVMBrick;
import org.catrobat.catroid.content.bricks.StoreCSVIntoUserListBrick;
import org.catrobat.catroid.content.bricks.StringToTableBrick;
import org.catrobat.catroid.content.bricks.SubCategoryHeaderBrick;
import org.catrobat.catroid.content.bricks.TableToFloatBrick;
import org.catrobat.catroid.content.bricks.TapAtBrick;
import org.catrobat.catroid.content.bricks.TapForBrick;
import org.catrobat.catroid.content.bricks.TestBrick;
import org.catrobat.catroid.content.bricks.ThinkBubbleBrick;
import org.catrobat.catroid.content.bricks.ThinkForBubbleBrick;
import org.catrobat.catroid.content.bricks.ThreedAlignNormalBrick;
import org.catrobat.catroid.content.bricks.ThreedAttachObjectToBoneBrick;
import org.catrobat.catroid.content.bricks.ThreedBindBoneToObjectBrick;
import org.catrobat.catroid.content.bricks.ThreedCreateCylinderBrick;
import org.catrobat.catroid.content.bricks.ThreedCreateFixedConstraintBrick;
import org.catrobat.catroid.content.bricks.ToggleDisplayBrick;
import org.catrobat.catroid.content.bricks.TouchAndSlideBrick;
import org.catrobat.catroid.content.bricks.TripleStitchBrick;
import org.catrobat.catroid.content.bricks.TryCatchFinallyBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.content.bricks.TurnLeftSpeedBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;
import org.catrobat.catroid.content.bricks.TurnRightSpeedBrick;
import org.catrobat.catroid.content.bricks.UnloadNNBrick;
import org.catrobat.catroid.content.bricks.UnpinFromCameraBrick;
import org.catrobat.catroid.content.bricks.UnzipBrick;
import org.catrobat.catroid.content.bricks.UnzipProjectFilesBrick;
import org.catrobat.catroid.content.bricks.UpdateManifestBrick;
import org.catrobat.catroid.content.bricks.UploadFileBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.content.bricks.VibrationBrick;
import org.catrobat.catroid.content.bricks.VmRelativeMouseMoveBrick;
import org.catrobat.catroid.content.bricks.VmSetMonitorSizeBrick;
import org.catrobat.catroid.content.bricks.VoxelBuildBrick;
import org.catrobat.catroid.content.bricks.VoxelConfigBrick;
import org.catrobat.catroid.content.bricks.VoxelCreateWorldBrick;
import org.catrobat.catroid.content.bricks.VoxelDeleteBrick;
import org.catrobat.catroid.content.bricks.VoxelLoadStringBrick;
import org.catrobat.catroid.content.bricks.VoxelSetBlockBrick;
import org.catrobat.catroid.content.bricks.VoxelSetTransparentBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WaitThreadBrick;
import org.catrobat.catroid.content.bricks.WaitTillIdleBrick;
import org.catrobat.catroid.content.bricks.WaitUntilBrick;
import org.catrobat.catroid.content.bricks.WebRequestBrick;
import org.catrobat.catroid.content.bricks.WhenAfterUpdateBrick;
import org.catrobat.catroid.content.bricks.WhenAppMinimizedBrick;
import org.catrobat.catroid.content.bricks.WhenAppRestoredBrick;
import org.catrobat.catroid.content.bricks.WhenBackPressedBrick;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.content.bricks.WhenBeforeUpdateBrick;
import org.catrobat.catroid.content.bricks.WhenBounceOffBrick;
import org.catrobat.catroid.content.bricks.WhenBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.bricks.WhenClonedWithNameBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.bricks.WhenFingerMovedOnScreenBrick;
import org.catrobat.catroid.content.bricks.WhenFingerMovedOverSpriteBrick;
import org.catrobat.catroid.content.bricks.WhenGamepadButtonBrick;
import org.catrobat.catroid.content.bricks.WhenMqttMessageBrick;
import org.catrobat.catroid.content.bricks.WhenNfcBrick;
import org.catrobat.catroid.content.bricks.WhenNotificationActionTriggeredBrick;
import org.catrobat.catroid.content.bricks.WhenNotificationClickedBrick;
import org.catrobat.catroid.content.bricks.WhenProjectExitsBrick;
import org.catrobat.catroid.content.bricks.WhenRaspiPinChangedBrick;
import org.catrobat.catroid.content.bricks.WhenSpriteReleasedBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.content.bricks.WhenTouchDownBrick;
import org.catrobat.catroid.content.bricks.WhenWindowResizedBrick;
import org.catrobat.catroid.content.bricks.WriteBaseBrick;
import org.catrobat.catroid.content.bricks.WriteEmbroideryToFileBrick;
import org.catrobat.catroid.content.bricks.WriteListOnDeviceBrick;
import org.catrobat.catroid.content.bricks.WriteToFilesBrick;
import org.catrobat.catroid.content.bricks.WriteVariableOnDeviceBrick;
import org.catrobat.catroid.content.bricks.WriteVariableToFileBrick;
import org.catrobat.catroid.content.bricks.ZigZagStitchBrick;
import org.catrobat.catroid.content.bricks.ZipBrick;
import org.catrobat.catroid.content.bricks.ZipProjectFilesBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.libraries.CustomBrickDefinition;
import org.catrobat.catroid.libraries.CustomBrickManager;
import org.catrobat.catroid.ui.controller.RecentBrickListManager;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.FavoriteBricksManager;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/CategoryBricksFactory.class
  classes14.dex
 */
/* JADX INFO: compiled from: CategoryBricksFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b%\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007J&\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001c\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0004J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\u0016\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002¨\u00060"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/CategoryBricksFactory;", "", "()V", "getAllAvailableBricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Landroid/content/Context;", "isBackgroundSprite", "", "getBrickCategory", "", "brick", "getBricks", "category", "getPreferenceKeyForCategory", "searchList", "searchBrick", SchemaSymbols.ATTVAL_LIST, "setupArduinoCategoryList", "setupAssertionsCategoryList", "setupChromecastCategoryList", "setupControlCategoryList", "setupDataCategoryList", "setupDeviceCategoryList", "setupDroneCategoryList", "setupEmbroideryCategoryList", "setupEventCategoryList", "setupFast2dCategoryList", "setupFavoriteBricksCategoryList", "setupFileCategoryList", "setupInternetCategoryList", "setupJumpingSumoCategoryList", "setupLegoEv3CategoryList", "setupLegoNxtCategoryList", "setupLibrariesCategoryList", "setupLooksCategoryList", "setupMotionCategoryList", "setupNeuralCategoryList", "setupPenCategoryList", "setupPhiroProCategoryList", "setupPlotCategoryList", "setupPocketensorCategoryList", "setupRaspiCategoryList", "setupRecentBricksCategoryList", "setupSoundCategoryList", "setupThreedCategoryList", "setupUserBricksCategoryList", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CategoryBricksFactory {
    public List<Brick> getBricks(String category, boolean isBackgroundSprite, Context context) {
        List<Brick> listEmptyList;
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(context, "context");
        if (Intrinsics.areEqual(category, context.getString(R.string.category_favorites))) {
            return setupFavoriteBricksCategoryList(context, isBackgroundSprite);
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_recently_used))) {
            return setupRecentBricksCategoryList(isBackgroundSprite);
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_pen))) {
            return setupPenCategoryList(isBackgroundSprite);
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_event))) {
            listEmptyList = setupEventCategoryList(context, isBackgroundSprite);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_control))) {
            listEmptyList = setupControlCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_motion))) {
            listEmptyList = setupMotionCategoryList(context, isBackgroundSprite);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_sound))) {
            listEmptyList = setupSoundCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_looks))) {
            listEmptyList = setupLooksCategoryList(context, isBackgroundSprite);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_user_bricks))) {
            listEmptyList = setupUserBricksCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_data))) {
            listEmptyList = setupDataCategoryList(context, isBackgroundSprite);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_device))) {
            listEmptyList = setupDeviceCategoryList(context, isBackgroundSprite);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_lego_nxt))) {
            listEmptyList = setupLegoNxtCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_lego_ev3))) {
            listEmptyList = setupLegoEv3CategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_arduino))) {
            listEmptyList = setupArduinoCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_drone))) {
            listEmptyList = setupDroneCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_jumping_sumo))) {
            listEmptyList = setupJumpingSumoCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_phiro))) {
            listEmptyList = setupPhiroProCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_cast))) {
            listEmptyList = setupChromecastCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_raspi))) {
            listEmptyList = setupRaspiCategoryList();
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_embroidery))) {
            listEmptyList = setupEmbroideryCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_plot))) {
            listEmptyList = setupPlotCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_neural))) {
            listEmptyList = setupNeuralCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.pocketensor))) {
            listEmptyList = setupPocketensorCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.fast2d))) {
            listEmptyList = setupFast2dCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_file))) {
            listEmptyList = setupFileCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_threed))) {
            listEmptyList = setupThreedCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_internet))) {
            listEmptyList = setupInternetCategoryList(context);
        } else if (Intrinsics.areEqual(category, context.getString(R.string.category_assertions))) {
            listEmptyList = setupAssertionsCategoryList(context);
        } else {
            listEmptyList = Intrinsics.areEqual(category, context.getString(R.string.category_libraries)) ? setupLibrariesCategoryList() : CollectionsKt.emptyList();
        }
        String preferenceKeyForCategory = getPreferenceKeyForCategory(category, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(category, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : listEmptyList) {
                    if (!(((Brick) obj) instanceof SubCategoryHeaderBrick)) {
                        arrayList.add(obj);
                    }
                }
                return arrayList;
            }
        }
        return listEmptyList;
    }

    private final String getPreferenceKeyForCategory(String category, Context context) {
        if (Intrinsics.areEqual(category, context.getString(R.string.category_event))) {
            return "pref_grouping_event";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_control))) {
            return "pref_grouping_control";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_motion))) {
            return "pref_grouping_motion";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_sound))) {
            return "pref_grouping_sound";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_looks))) {
            return "pref_grouping_looks";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_data))) {
            return "pref_grouping_data";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_device))) {
            return "pref_grouping_device";
        }
        if (Intrinsics.areEqual(category, context.getString(R.string.category_threed))) {
            return "pref_grouping_threed";
        }
        return null;
    }

    private final List<Brick> setupLibrariesCategoryList() {
        ArrayList arrayList = new ArrayList();
        Iterator<CustomBrickDefinition> it = CustomBrickManager.INSTANCE.getAllDefinitions().iterator();
        while (it.hasNext()) {
            arrayList.add(new CustomBrick(it.next().getId()));
        }
        return arrayList;
    }

    public final List<Brick> setupRecentBricksCategoryList(boolean isBackgroundSprite) {
        List<Brick> recentBricks = RecentBrickListManager.getInstance().getRecentBricks(isBackgroundSprite);
        Intrinsics.checkNotNullExpressionValue(recentBricks, "getRecentBricks(...)");
        return recentBricks;
    }

    protected List<Brick> setupEventCategoryList(Context context, boolean isBackgroundSprite) {
        BroadcastMessageContainer broadcastMessageContainer;
        BroadcastMessageContainer broadcastMessageContainer2;
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_event);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.SMALLER_THAN.toString(), null);
                formulaElement.setLeftChild(new FormulaElement(FormulaElement.ElementType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, null));
                formulaElement.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "2", null));
                ArrayList arrayList = new ArrayList();
                arrayList.add(new WhenStartedBrick());
                arrayList.add(new WhenBrick());
                arrayList.add(new WhenTouchDownBrick());
                arrayList.add(new WhenSpriteReleasedBrick());
                arrayList.add(new WhenFingerMovedOverSpriteBrick());
                arrayList.add(new WhenFingerMovedOnScreenBrick());
                Project currentProject = ProjectManager.getInstance().getCurrentProject();
                List<String> broadcastMessages = (currentProject == null || (broadcastMessageContainer2 = currentProject.getBroadcastMessageContainer()) == null) ? null : broadcastMessageContainer2.getBroadcastMessages();
                String string2 = context.getString(R.string.brick_broadcast_default_value);
                if (broadcastMessages != null && broadcastMessages.size() > 0) {
                    string2 = broadcastMessages.get(0);
                }
                arrayList.add(new BroadcastReceiverBrick(new BroadcastScript(string2)));
                arrayList.add(new BroadcastBrick(string2));
                arrayList.add(new BroadcastWaitBrick(string2));
                arrayList.add(new BroadcastWithParamsReceiverBrick(string2, null));
                arrayList.add(new BroadcastWithParamsBrick(string2, "any data..."));
                arrayList.add(new WhenNotificationClickedBrick(new WhenNotificationClickedScript(new Formula((Integer) 123456))));
                arrayList.add(new WhenNotificationActionTriggeredBrick(new WhenNotificationActionTriggeredScript(new Formula("123"))));
                arrayList.add(new WhenProjectExitsBrick());
                arrayList.add(new WhenBackPressedBrick());
                arrayList.add(new WhenConditionBrick(new WhenConditionScript(new Formula(formulaElement))));
                if (!isBackgroundSprite) {
                    arrayList.add(new WhenBounceOffBrick(new WhenBounceOffScript(null)));
                }
                arrayList.add(new WhenBackgroundChangesBrick());
                arrayList.add(new WhenClonedBrick());
                arrayList.add(new CloneBrick());
                arrayList.add(new DeleteThisCloneBrick());
                arrayList.add(new WhenClonedWithNameBrick(new WhenClonedWithNameScript(new Formula("clone"))));
                arrayList.add(new CloneAndNameBrick("clone"));
                arrayList.add(new WhenBeforeUpdateBrick());
                arrayList.add(new WhenAfterUpdateBrick());
                arrayList.add(new WhenAppMinimizedBrick());
                arrayList.add(new WhenAppRestoredBrick());
                arrayList.add(new WhenWindowResizedBrick());
                arrayList.add(new WhenMqttMessageBrick(new WhenMqttMessageScript(new Formula("room_1"))));
                if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
                    arrayList.add(new WhenNfcBrick());
                }
                return arrayList;
            }
        }
        FormulaElement formulaElement2 = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.SMALLER_THAN.toString(), null);
        formulaElement2.setLeftChild(new FormulaElement(FormulaElement.ElementType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, null));
        formulaElement2.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "2", null));
        ArrayList arrayList2 = new ArrayList();
        WhenStartedBrick whenStartedBrick = new WhenStartedBrick();
        String string3 = context.getString(R.string.subcategory_event_touch);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        WhenStartedBrick whenStartedBrick2 = whenStartedBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string3, whenStartedBrick2));
        arrayList2.add(new WhenStartedBrick());
        arrayList2.add(new WhenBrick());
        arrayList2.add(new WhenTouchDownBrick());
        arrayList2.add(new WhenSpriteReleasedBrick());
        arrayList2.add(new WhenFingerMovedOverSpriteBrick());
        arrayList2.add(new WhenFingerMovedOnScreenBrick());
        arrayList2.add(new WhenBackPressedBrick());
        String string4 = context.getString(R.string.subcategory_event_messages);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string4, whenStartedBrick2));
        Project currentProject2 = ProjectManager.getInstance().getCurrentProject();
        List<String> broadcastMessages2 = (currentProject2 == null || (broadcastMessageContainer = currentProject2.getBroadcastMessageContainer()) == null) ? null : broadcastMessageContainer.getBroadcastMessages();
        String string5 = context.getString(R.string.brick_broadcast_default_value);
        if (broadcastMessages2 != null && broadcastMessages2.size() > 0) {
            string5 = broadcastMessages2.get(0);
        }
        arrayList2.add(new BroadcastReceiverBrick(new BroadcastScript(string5)));
        arrayList2.add(new BroadcastBrick(string5));
        arrayList2.add(new BroadcastWaitBrick(string5));
        arrayList2.add(new BroadcastWithParamsReceiverBrick(string5, null));
        arrayList2.add(new BroadcastWithParamsBrick(string5, "any data..."));
        arrayList2.add(new WhenNotificationClickedBrick(new WhenNotificationClickedScript(new Formula((Integer) 123456))));
        arrayList2.add(new WhenNotificationActionTriggeredBrick(new WhenNotificationActionTriggeredScript(new Formula("123"))));
        arrayList2.add(new WhenMqttMessageBrick(new WhenMqttMessageScript(new Formula("room_1"))));
        String string6 = context.getString(R.string.subcategory_event_conditions);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string6, whenStartedBrick2));
        arrayList2.add(new WhenConditionBrick(new WhenConditionScript(new Formula(formulaElement2))));
        if (!isBackgroundSprite) {
            arrayList2.add(new WhenBounceOffBrick(new WhenBounceOffScript(null)));
        }
        arrayList2.add(new WhenBackgroundChangesBrick());
        String string7 = context.getString(R.string.subcategory_event_cloning);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string7, whenStartedBrick2));
        arrayList2.add(new WhenClonedBrick());
        arrayList2.add(new CloneBrick());
        arrayList2.add(new DeleteThisCloneBrick());
        arrayList2.add(new WhenClonedWithNameBrick(new WhenClonedWithNameScript(new Formula("clone"))));
        arrayList2.add(new CloneAndNameBrick("clone"));
        String string8 = context.getString(R.string.subcategory_event_system);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string8, whenStartedBrick2));
        arrayList2.add(new WhenProjectExitsBrick());
        arrayList2.add(new WhenBeforeUpdateBrick());
        arrayList2.add(new WhenAfterUpdateBrick());
        arrayList2.add(new WhenAppMinimizedBrick());
        arrayList2.add(new WhenAppRestoredBrick());
        arrayList2.add(new WhenWindowResizedBrick());
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            arrayList2.add(new WhenNfcBrick());
        }
        return arrayList2;
    }

    protected List<Brick> setupControlCategoryList(Context context) {
        BroadcastMessageContainer broadcastMessageContainer;
        int i;
        List<String> broadcastMessages;
        BroadcastMessageContainer broadcastMessageContainer2;
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_control);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.SMALLER_THAN.toString(), null);
                formulaElement.setLeftChild(new FormulaElement(FormulaElement.ElementType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, null));
                formulaElement.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "2", null));
                Formula formula = new Formula(formulaElement);
                ArrayList arrayList = new ArrayList();
                arrayList.add(new WaitBrick(1000));
                arrayList.add(new NoteBrick(context.getString(R.string.brick_note_default_value)));
                arrayList.add(new ForeverBrick());
                arrayList.add(new IfLogicBeginBrick(formula));
                arrayList.add(new IfThenLogicBeginBrick(formula));
                arrayList.add(new AsyncRepeatBrick(new Formula((Integer) 3)));
                arrayList.add(new IntervalRepeatBrick(new Formula((Integer) 3), new Formula((Integer) 1)));
                arrayList.add(new WaitUntilBrick(formula));
                arrayList.add(new RepeatBrick(new Formula((Integer) 10)));
                arrayList.add(new RepeatUntilBrick(formula));
                arrayList.add(new ForVariableFromToBrick(new Formula((Integer) 1), new Formula((Integer) 10)));
                arrayList.add(new ForItemInUserListBrick());
                arrayList.add(new RunAsSpriteBrick(new Formula("Sprite")));
                arrayList.add(new InstantBrick());
                arrayList.add(new SpawnThreadBrick(new Formula("thread1")));
                arrayList.add(new WaitThreadBrick(new Formula("thread1")));
                arrayList.add(new StopThreadBrick(new Formula("thread1")));
                arrayList.add(new SceneTransitionBrick(null));
                arrayList.add(new SceneStartBrick(null));
                arrayList.add(new SceneIdBrick(SchemaSymbols.ATTVAL_TRUE_1));
                arrayList.add(new ClearSceneBrick(null));
                arrayList.add(new SetSaveScenesBrick((Integer) 1));
                arrayList.add(new SetStopSoundsBrick((Integer) 1));
                arrayList.add(new LaunchProjectBrick("project.newtrobat"));
                arrayList.add(new ReturnToPreviousProjectBrick());
                if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
                    arrayList.add(new PhiroIfLogicBeginBrick());
                }
                arrayList.add(new ExitStageBrick());
                arrayList.add(new StopScriptBrick(0));
                arrayList.add(new WaitTillIdleBrick());
                arrayList.add(new TryCatchFinallyBrick());
                arrayList.add(new WhenClonedBrick());
                arrayList.add(new CloneBrick());
                arrayList.add(new DeleteThisCloneBrick());
                arrayList.add(new WhenClonedWithNameBrick(new WhenClonedWithNameScript(new Formula("clone"))));
                arrayList.add(new CloneAndNameBrick("clone"));
                if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
                    arrayList.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
                }
                Project currentProject = ProjectManager.getInstance().getCurrentProject();
                if (currentProject == null || (broadcastMessageContainer2 = currentProject.getBroadcastMessageContainer()) == null) {
                    i = R.string.brick_broadcast_default_value;
                    broadcastMessages = null;
                } else {
                    broadcastMessages = broadcastMessageContainer2.getBroadcastMessages();
                    i = R.string.brick_broadcast_default_value;
                }
                String string2 = context.getString(i);
                if (broadcastMessages != null && broadcastMessages.size() > 0) {
                    string2 = broadcastMessages.get(0);
                }
                arrayList.add(new BroadcastReceiverBrick(new BroadcastScript(string2)));
                arrayList.add(new BroadcastBrick(string2));
                arrayList.add(new BroadcastWaitBrick(string2));
                arrayList.add(new TapAtBrick(-100, BrickValues.TOUCH_Y_START));
                arrayList.add(new TapForBrick(-100, BrickValues.TOUCH_Y_START, 0.3d));
                arrayList.add(new TouchAndSlideBrick(-100, BrickValues.TOUCH_Y_START, 100, 200, 0.3d));
                arrayList.add(new OpenUrlBrick(BrickValues.OPEN_IN_BROWSER));
                arrayList.add(new OpenAppBrick(new Formula("com.android.settings")));
                return arrayList;
            }
        }
        FormulaElement formulaElement2 = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.SMALLER_THAN.toString(), null);
        formulaElement2.setLeftChild(new FormulaElement(FormulaElement.ElementType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, null));
        formulaElement2.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "2", null));
        Formula formula2 = new Formula(formulaElement2);
        ArrayList arrayList2 = new ArrayList();
        WaitBrick waitBrick = new WaitBrick(1000);
        String string3 = context.getString(R.string.subcategory_control_waiting);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        WaitBrick waitBrick2 = waitBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string3, waitBrick2));
        arrayList2.add(new WaitBrick(1000));
        arrayList2.add(new WaitUntilBrick(formula2));
        arrayList2.add(new WaitTillIdleBrick());
        arrayList2.add(new WaitThreadBrick(new Formula("thread1")));
        arrayList2.add(new NoteBrick(context.getString(R.string.brick_note_default_value)));
        String string4 = context.getString(R.string.subcategory_control_loops);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string4, waitBrick2));
        arrayList2.add(new ForeverBrick());
        arrayList2.add(new RepeatBrick(new Formula((Integer) 10)));
        arrayList2.add(new RepeatUntilBrick(formula2));
        arrayList2.add(new AsyncRepeatBrick(new Formula((Integer) 3)));
        arrayList2.add(new IntervalRepeatBrick(new Formula((Integer) 3), new Formula((Integer) 1)));
        arrayList2.add(new ForVariableFromToBrick(new Formula((Integer) 1), new Formula((Integer) 10)));
        arrayList2.add(new ForItemInUserListBrick());
        arrayList2.add(new InstantBrick());
        arrayList2.add(new SpawnThreadBrick(new Formula("thread1")));
        String string5 = context.getString(R.string.subcategory_control_conditions);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string5, waitBrick2));
        arrayList2.add(new IfLogicBeginBrick(formula2));
        arrayList2.add(new IfThenLogicBeginBrick(formula2));
        arrayList2.add(new TryCatchFinallyBrick());
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            arrayList2.add(new PhiroIfLogicBeginBrick());
        }
        String string6 = context.getString(R.string.subcategory_control_scenes);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string6, waitBrick2));
        arrayList2.add(new SceneStartBrick(null));
        arrayList2.add(new SceneIdBrick(SchemaSymbols.ATTVAL_TRUE_1));
        arrayList2.add(new SceneTransitionBrick(null));
        arrayList2.add(new ClearSceneBrick(null));
        arrayList2.add(new SetSaveScenesBrick((Integer) 1));
        arrayList2.add(new LaunchProjectBrick("project.newtrobat"));
        arrayList2.add(new ReturnToPreviousProjectBrick());
        String string7 = context.getString(R.string.subcategory_control_stopping);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string7, waitBrick2));
        arrayList2.add(new StopScriptBrick(0));
        arrayList2.add(new StopThreadBrick(new Formula("thread1")));
        arrayList2.add(new ExitStageBrick());
        String string8 = context.getString(R.string.subcategory_control_cloning);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string8, waitBrick2));
        arrayList2.add(new WhenClonedBrick());
        arrayList2.add(new CloneBrick());
        arrayList2.add(new DeleteThisCloneBrick());
        arrayList2.add(new WhenClonedWithNameBrick(new WhenClonedWithNameScript(new Formula("clone"))));
        arrayList2.add(new CloneAndNameBrick("clone"));
        arrayList2.add(new RunAsSpriteBrick(new Formula("Sprite")));
        String string9 = context.getString(R.string.subcategory_control_messages);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string9, waitBrick2));
        Project currentProject2 = ProjectManager.getInstance().getCurrentProject();
        List<String> broadcastMessages2 = (currentProject2 == null || (broadcastMessageContainer = currentProject2.getBroadcastMessageContainer()) == null) ? null : broadcastMessageContainer.getBroadcastMessages();
        String string10 = context.getString(R.string.brick_broadcast_default_value);
        if (broadcastMessages2 != null && broadcastMessages2.size() > 0) {
            string10 = broadcastMessages2.get(0);
        }
        arrayList2.add(new BroadcastReceiverBrick(new BroadcastScript(string10)));
        arrayList2.add(new BroadcastBrick(string10));
        arrayList2.add(new BroadcastWaitBrick(string10));
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            arrayList2.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
        }
        String string11 = context.getString(R.string.subcategory_control_touches);
        Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string11, waitBrick2));
        arrayList2.add(new TapAtBrick(-100, BrickValues.TOUCH_Y_START));
        arrayList2.add(new TapForBrick(-100, BrickValues.TOUCH_Y_START, 0.3d));
        arrayList2.add(new TouchAndSlideBrick(-100, BrickValues.TOUCH_Y_START, 100, 200, 0.3d));
        arrayList2.add(new OpenUrlBrick(BrickValues.OPEN_IN_BROWSER));
        arrayList2.add(new OpenAppBrick(new Formula("com.android.settings")));
        return arrayList2;
    }

    private final List<Brick> setupUserBricksCategoryList() {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ArrayList arrayList = new ArrayList();
        if (currentSprite != null) {
            arrayList = currentSprite.getUserDefinedBrickList();
            Intrinsics.checkNotNullExpressionValue(arrayList, "getUserDefinedBrickList(...)");
        }
        return new ArrayList(arrayList);
    }

    private final List<Brick> setupChromecastCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WhenGamepadButtonBrick(new WhenGamepadButtonScript(context.getString(R.string.cast_gamepad_A))));
        return arrayList;
    }

    protected List<Brick> setupMotionCategoryList(Context context, boolean isBackgroundSprite) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_motion);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        DefaultConstructorMarker defaultConstructorMarker = null;
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new PlaceAtBrick(100, 200));
                arrayList.add(new SetXBrick(new Formula((Integer) 100)));
                arrayList.add(new SetYBrick(200));
                arrayList.add(new ChangeXByNBrick(10));
                arrayList.add(new ChangeYByNBrick(10));
                arrayList.add(new GoToBrick(null));
                if (!isBackgroundSprite) {
                    arrayList.add(new IfOnEdgeBounceBrick());
                }
                arrayList.add(new MoveNStepsBrick(10.0d));
                arrayList.add(new TurnLeftBrick(15.0d));
                arrayList.add(new TurnRightBrick(15.0d));
                arrayList.add(new PointInDirectionBrick(90.0d));
                arrayList.add(new PointToBrick(null));
                arrayList.add(new SetRotationStyleBrick());
                arrayList.add(new GlideToBrick(100, 200, 1000));
                if (!isBackgroundSprite) {
                    arrayList.add(new GoNStepsBackBrick(1));
                    arrayList.add(new ComeToFrontBrick());
                }
                arrayList.add(new SetCameraFocusPointBrick());
                arrayList.add(new VibrationBrick(1.0d));
                arrayList.add(new SetPhysicsObjectTypeBrick(BrickValues.PHYSIC_TYPE));
                if (!isBackgroundSprite) {
                    arrayList.add(new WhenBounceOffBrick(new WhenBounceOffScript(null)));
                }
                arrayList.add(new SetHitboxBrick());
                arrayList.add(new SetVelocityBrick(BrickValues.PHYSIC_VELOCITY));
                arrayList.add(new ApplyForceBrick(10, 100));
                arrayList.add(new ApplyImpulseBrick(100, 10));
                arrayList.add(new TurnLeftSpeedBrick(15.0d));
                arrayList.add(new TurnRightSpeedBrick(15.0d));
                arrayList.add(new ApplyTorqueBrick(15));
                arrayList.add(new ApplyAngularImpulseBrick(30));
                arrayList.add(new SetGravityBrick(BrickValues.PHYSIC_GRAVITY));
                arrayList.add(new SetMassBrick(1.0d));
                arrayList.add(new SetDampingBrick(10.0f, 10.0f));
                arrayList.add(new CreateRevoluteJointBrick("joint", "sprite2", 10, 200));
                arrayList.add(new CreateDistanceJointBrick("joint", "sprite2", "100", "5", "0.3"));
                arrayList.add(new CreateWeldJointBrick("joint", "sprite2", 10, 200));
                arrayList.add(new CreatePrismaticJointBrick("joint", "sprite2", 10, 100, 20, 30));
                arrayList.add(new CreatePulleyJointBrick("joint", "sprite1", "sprite2", 10, 100, 0, 0, 1.0f));
                arrayList.add(new CreateGearJointBrick("joint", "jointA", "jointB", 1.0f));
                arrayList.add(new DestroyJointBrick("joint"));
                arrayList.add(new PerformRayCastBrick("ray", 0, 0, 300, 500));
                arrayList.add(new SetBounceBrick(80.0d));
                arrayList.add(new SetFrictionBrick(20.0d));
                if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
                    arrayList.add(new PhiroMotorMoveForwardBrick(PhiroMotorMoveForwardBrick.Motor.MOTOR_LEFT, 100));
                    arrayList.add(new PhiroMotorMoveBackwardBrick(PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT, 100));
                    arrayList.add(new PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH));
                }
                arrayList.add(new FadeParticleEffectBrick(0, 1, defaultConstructorMarker));
                return arrayList;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        SetXBrick setXBrick = new SetXBrick(100);
        String string2 = context.getString(R.string.subcategory_motion_coordinates);
        if (string2 == null) {
            string2 = "";
        }
        SetXBrick setXBrick2 = setXBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string2, setXBrick2));
        arrayList2.add(new PlaceAtBrick(100, 200));
        arrayList2.add(new SetXBrick(new Formula((Integer) 100)));
        arrayList2.add(new SetYBrick(200));
        arrayList2.add(new ChangeXByNBrick(10));
        arrayList2.add(new ChangeYByNBrick(10));
        arrayList2.add(new GoToBrick(null));
        arrayList2.add(new GlideToBrick(100, 200, 1000));
        String string3 = context.getString(R.string.subcategory_motion_rotation);
        if (string3 == null) {
            string3 = "";
        }
        arrayList2.add(new SubCategoryHeaderBrick(string3, setXBrick2));
        arrayList2.add(new MoveNStepsBrick(10.0d));
        arrayList2.add(new TurnLeftBrick(15.0d));
        arrayList2.add(new TurnRightBrick(15.0d));
        arrayList2.add(new PointInDirectionBrick(90.0d));
        arrayList2.add(new PointToBrick(null));
        arrayList2.add(new SetRotationStyleBrick());
        String string4 = context.getString(R.string.subcategory_motion_layers);
        if (string4 == null) {
            string4 = "";
        }
        arrayList2.add(new SubCategoryHeaderBrick(string4, setXBrick2));
        if (!isBackgroundSprite) {
            arrayList2.add(new GoNStepsBackBrick(1));
            arrayList2.add(new ComeToFrontBrick());
        }
        arrayList2.add(new SetCameraFocusPointBrick());
        String string5 = context.getString(R.string.subcategory_motion_physics_core);
        if (string5 == null) {
            string5 = "";
        }
        arrayList2.add(new SubCategoryHeaderBrick(string5, setXBrick2));
        arrayList2.add(new SetPhysicsObjectTypeBrick(BrickValues.PHYSIC_TYPE));
        arrayList2.add(new SetHitboxBrick());
        if (!isBackgroundSprite) {
            arrayList2.add(new IfOnEdgeBounceBrick());
            arrayList2.add(new WhenBounceOffBrick(new WhenBounceOffScript(null)));
        }
        arrayList2.add(new VibrationBrick(1.0d));
        String string6 = context.getString(R.string.subcategory_motion_physics_forces);
        if (string6 == null) {
            string6 = "";
        }
        arrayList2.add(new SubCategoryHeaderBrick(string6, setXBrick2));
        arrayList2.add(new SetVelocityBrick(BrickValues.PHYSIC_VELOCITY));
        arrayList2.add(new ApplyForceBrick(10, 100));
        arrayList2.add(new ApplyImpulseBrick(100, 10));
        arrayList2.add(new ApplyTorqueBrick(15));
        arrayList2.add(new ApplyAngularImpulseBrick(30));
        arrayList2.add(new TurnLeftSpeedBrick(15.0d));
        arrayList2.add(new TurnRightSpeedBrick(15.0d));
        arrayList2.add(new SetGravityBrick(BrickValues.PHYSIC_GRAVITY));
        arrayList2.add(new SetMassBrick(1.0d));
        arrayList2.add(new SetDampingBrick(10.0f, 10.0f));
        arrayList2.add(new SetBounceBrick(80.0d));
        arrayList2.add(new SetFrictionBrick(20.0d));
        arrayList2.add(new PerformRayCastBrick("ray", 0, 0, 300, 500));
        String string7 = context.getString(R.string.subcategory_motion_physics_joints);
        if (string7 == null) {
            string7 = "";
        }
        arrayList2.add(new SubCategoryHeaderBrick(string7, setXBrick2));
        arrayList2.add(new CreateRevoluteJointBrick("joint", "sprite2", 10, 200));
        arrayList2.add(new CreateDistanceJointBrick("joint", "sprite2", "100", "5", "0.3"));
        arrayList2.add(new CreateWeldJointBrick("joint", "sprite2", 10, 200));
        arrayList2.add(new CreatePrismaticJointBrick("joint", "sprite2", 10, 100, 20, 30));
        arrayList2.add(new CreatePulleyJointBrick("joint", "sprite1", "sprite2", 10, 100, 0, 0, 1.0f));
        arrayList2.add(new CreateGearJointBrick("joint", "jointA", "jointB", 1.0f));
        arrayList2.add(new DestroyJointBrick("joint"));
        String string8 = context.getString(R.string.subcategory_motion_robots);
        arrayList2.add(new SubCategoryHeaderBrick(string8 != null ? string8 : "", setXBrick2));
        arrayList2.add(new FadeParticleEffectBrick(0, 1, defaultConstructorMarker));
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            arrayList2.add(new PhiroMotorMoveForwardBrick(PhiroMotorMoveForwardBrick.Motor.MOTOR_LEFT, 100));
            arrayList2.add(new PhiroMotorMoveBackwardBrick(PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT, 100));
            arrayList2.add(new PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH));
        }
        return arrayList2;
    }

    protected List<Brick> setupSoundCategoryList(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_sound);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        Double dValueOf = Double.valueOf(-10.0d);
        Double dValueOf2 = Double.valueOf(50.0d);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new PlaySoundBrick());
                arrayList.add(new PlaySoundAndWaitBrick());
                arrayList.add(new PlaySoundAtBrick(0.5d));
                arrayList.add(new SoundFileBrick("my_sound.mp3"));
                arrayList.add(new SoundFilesBrick("my_sound_FROM_PROJECT_FILES.mp3"));
                arrayList.add(new PrepareSoundBrick("my_sound.mp3", "sound"));
                arrayList.add(new PlayPreparedSoundBrick("sound"));
                arrayList.add(new StopSoundBrick());
                arrayList.add(new StopAllSoundsBrick());
                arrayList.add(new SetVolumeToBrick(60.0d));
                arrayList.add(new SetSoundVolumeBrick(dValueOf2));
                arrayList.add(new ChangeVolumeByNBrick(new Formula(dValueOf)));
                arrayList.add(new ListenMicroBrick("100"));
                arrayList.add(new StartRecordingBrick());
                arrayList.add(new StopRecordingBrick("audio.mp3"));
                if (SettingsFragment.isAISpeechSynthetizationSharedPreferenceEnabled(context)) {
                    arrayList.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
                    arrayList.add(new SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)));
                }
                if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
                    arrayList.add(new PhiroPlayToneBrick(PhiroPlayToneBrick.Tone.DO, 1));
                }
                if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
                    arrayList.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
                    arrayList.add(new StartListeningBrick());
                    arrayList.add(new SetListeningLanguageBrick());
                }
                arrayList.add(new SetStopSoundsBrick((Integer) 1));
                arrayList.add(new SetInstrumentBrick());
                arrayList.add(new PlayNoteForBeatsBrick(70, 1));
                arrayList.add(new PlayDrumForBeatsBrick(1));
                arrayList.add(new SetTempoBrick(60));
                arrayList.add(new ChangeTempoByNBrick(10));
                arrayList.add(new PauseForBeatsBrick(1.0f));
                return arrayList;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        PlayPreparedSoundBrick playPreparedSoundBrick = new PlayPreparedSoundBrick();
        String string2 = context.getString(R.string.subcategory_sound_playback);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        PlayPreparedSoundBrick playPreparedSoundBrick2 = playPreparedSoundBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string2, playPreparedSoundBrick2));
        arrayList2.add(new PlaySoundBrick());
        arrayList2.add(new PlaySoundAndWaitBrick());
        arrayList2.add(new PlaySoundAtBrick(0.5d));
        arrayList2.add(new PrepareSoundBrick("my_sound.mp3", "sound"));
        arrayList2.add(new PlayPreparedSoundBrick("sound"));
        arrayList2.add(new StopSoundBrick());
        arrayList2.add(new StopAllSoundsBrick());
        String string3 = context.getString(R.string.subcategory_sound_volume);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string3, playPreparedSoundBrick2));
        arrayList2.add(new SetVolumeToBrick(60.0d));
        arrayList2.add(new SetSoundVolumeBrick(dValueOf2));
        arrayList2.add(new ChangeVolumeByNBrick(new Formula(dValueOf)));
        arrayList2.add(new SetStopSoundsBrick((Integer) 1));
        String string4 = context.getString(R.string.subcategory_sound_files);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string4, playPreparedSoundBrick2));
        arrayList2.add(new SoundFileBrick("my_sound.mp3"));
        arrayList2.add(new SoundFilesBrick("my_sound_FROM_PROJECT_FILES.mp3"));
        arrayList2.add(new StartRecordingBrick());
        arrayList2.add(new StopRecordingBrick("audio.mp3"));
        String string5 = context.getString(R.string.subcategory_sound_speech);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string5, playPreparedSoundBrick2));
        arrayList2.add(new ListenMicroBrick("100"));
        if (SettingsFragment.isAISpeechSynthetizationSharedPreferenceEnabled(context)) {
            arrayList2.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
            arrayList2.add(new SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)));
        }
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            arrayList2.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
            arrayList2.add(new StartListeningBrick());
            arrayList2.add(new SetListeningLanguageBrick());
        }
        String string6 = context.getString(R.string.subcategory_sound_music);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string6, playPreparedSoundBrick2));
        arrayList2.add(new SetInstrumentBrick());
        arrayList2.add(new PlayNoteForBeatsBrick(70, 1));
        arrayList2.add(new PlayDrumForBeatsBrick(1));
        arrayList2.add(new SetTempoBrick(60));
        arrayList2.add(new ChangeTempoByNBrick(10));
        arrayList2.add(new PauseForBeatsBrick(1.0f));
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            arrayList2.add(new PhiroPlayToneBrick(PhiroPlayToneBrick.Tone.DO, 1));
        }
        return arrayList2;
    }

    protected List<Brick> setupLooksCategoryList(Context context, boolean isBackgroundSprite) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_looks);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                if (!isBackgroundSprite) {
                    arrayList.add(new SetLookBrick());
                    arrayList.add(new SetLookByIndexBrick(1));
                }
                arrayList.add(new SetWidthBrick(60.0d));
                arrayList.add(new SetHeightBrick(60.0d));
                arrayList.add(new ChangeWidthBrick(10.0d));
                arrayList.add(new ChangeHeightBrick(10.0d));
                arrayList.add(new NextLookBrick());
                arrayList.add(new PreviousLookBrick());
                arrayList.add(new PlayGifBrick("cat.gif"));
                arrayList.add(new StopGifBrick());
                arrayList.add(new PlaySpritesheetBrick(new Formula((Integer) 4), new Formula((Integer) 8), new Formula((Integer) 1), new Formula((Integer) 8), new Formula(Double.valueOf(0.1d))));
                arrayList.add(new StopSpritesheetBrick());
                arrayList.add(new SetSizeToBrick(60.0d));
                arrayList.add(new ChangeSizeByNBrick(10.0d));
                arrayList.add(new HideBrick());
                arrayList.add(new ShowBrick());
                arrayList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
                arrayList.add(new BigAskBrick(context.getString(R.string.brick_ask_default_question), "Введите ответ:", "OK", "Отмена", "Ваш ответ"));
                if (!isBackgroundSprite) {
                    arrayList.add(new SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)));
                    arrayList.add(new SayForBubbleBrick(context.getString(R.string.brick_say_bubble_default_value), 1.0f));
                    arrayList.add(new ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)));
                    arrayList.add(new ThinkForBubbleBrick(context.getString(R.string.brick_think_bubble_default_value), 1.0f));
                }
                arrayList.add(new ShowTextBrick(100, 200));
                arrayList.add(new ShowTextColorSizeAlignmentBrick(100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
                arrayList.add(new SetTransparencyBrick(50.0d));
                arrayList.add(new ChangeTransparencyByNBrick(25.0d));
                arrayList.add(new SetBrightnessBrick(50.0d));
                arrayList.add(new ChangeBrightnessByNBrick(25.0d));
                arrayList.add(new SetColorBrick(0.0d));
                arrayList.add(new ChangeColorByNBrick(25.0d));
                arrayList.add(new SetCornerOffsetsBrick(new Formula((Integer) 0), new Formula((Integer) 0), new Formula((Integer) 50), new Formula((Integer) 100), new Formula((Integer) 0), new Formula((Integer) 0), new Formula((Integer) 0), new Formula((Integer) 0)));
                arrayList.add(new EasePropertyBrick(0, 0, 100.0f, 250.0f, 1.0f));
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i = 0;
                int i2 = 1;
                arrayList.add(new FadeParticleEffectBrick(i, i2, defaultConstructorMarker));
                arrayList.add(new ParticleEffectAdditivityBrick(i, i2, defaultConstructorMarker));
                arrayList.add(new SetParticleColorBrick("#ff0000"));
                arrayList.add(new ClearGraphicEffectBrick());
                arrayList.add(new SetCameraFocusPointBrick());
                arrayList.add(new SetCameraPosition2Brick(100, 300));
                arrayList.add(new SetCameraRotation2Brick(90.0d));
                arrayList.add(new SetCameraZoomBrick(3.0d));
                arrayList.add(new PinToCameraBrick());
                arrayList.add(new UnpinFromCameraBrick());
                arrayList.add(new WhenBackgroundChangesBrick());
                arrayList.add(new SetBackgroundBrick());
                int i3 = 1;
                arrayList.add(new SetBackgroundByIndexBrick(1));
                arrayList.add(new SetBackgroundAndWaitBrick());
                arrayList.add(new SetBackgroundByIndexAndWaitBrick(1));
                if (!ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                    DefaultConstructorMarker defaultConstructorMarker2 = null;
                    boolean z = false;
                    arrayList.add(new CameraBrick(z, i3, defaultConstructorMarker2));
                    arrayList.add(new ChooseCameraBrick(z, i3, defaultConstructorMarker2));
                    arrayList.add(new FlashBrick());
                }
                if (!isBackgroundSprite) {
                    arrayList.add(new LookRequestBrick(BrickValues.LOOK_REQUEST));
                } else if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
                    arrayList.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE));
                } else {
                    arrayList.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST));
                }
                arrayList.add(new ScreenShotBrick());
                arrayList.add(new PhotoBrick());
                arrayList.add(new CutLookBrick((Integer) 100, (Integer) 200, (Integer) 300, (Integer) 400));
                arrayList.add(new SaveLookBrick("my_actor.png"));
                arrayList.add(new LookFileBrick("my_actor.png"));
                arrayList.add(new SaveLookFilesBrick("look.png"));
                arrayList.add(new SetLookFilesBrick("look.png"));
                arrayList.add(new LookToTableBrick("rTable", "gTable", "bTable", "aTable"));
                arrayList.add(new LookFromTableBrick("rTable", "gTable", "bTable", "aTable"));
                if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
                    arrayList.add(new PhiroRGBLightBrick(PhiroRGBLightBrick.Eye.BOTH, 0, 255, 255));
                }
                String string2 = context.getString(R.string.brick_paint_new_look_name);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                arrayList.add(new PaintNewLookBrick(string2));
                arrayList.add(new EditLookBrick());
                String string3 = context.getString(R.string.brick_copy_look_name);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                arrayList.add(new CopyLookBrick(string3));
                arrayList.add(new DeleteLookBrick());
                arrayList.add(new CreateDialogBrick("myDialog", "Фижма", "введите ваш ответ"));
                arrayList.add(new SetPositiveBrick("myDialog", "Да"));
                arrayList.add(new SetNeutralBrick("myDialog", "Позже"));
                arrayList.add(new SetNegativeBrick("myDialog", "Нет"));
                arrayList.add(new AddEditBrick("myDialog", "это текстовое поле"));
                arrayList.add(new AddRadioBrick("myDialog", "это выбор"));
                arrayList.add(new SetCallbackBrick("myDialog"));
                arrayList.add(new ShowDialogBrick("myDialog"));
                arrayList.add(new SquareBrick("square", "#ff0000", 0.0f, 0.0f, 100.0f, 100.0f, 1.0f, 0.0f, 0.0f));
                arrayList.add(new DelSquareBrick("square"));
                arrayList.add(new CreateBufferBrick("Map", "512", "512"));
                arrayList.add(new SetBufferModeBrick(new Formula("Map"), new Formula((Integer) 1), new Formula((Integer) 0)));
                arrayList.add(new SetBufferAutoUpdateBrick(new Formula("Map"), new Formula((Integer) 1)));
                arrayList.add(new AddToBufferBrick("Map"));
                arrayList.add(new RemoveFromBufferBrick(new Formula("Map")));
                arrayList.add(new SetBufferOnlyBrick(new Formula((Integer) 1)));
                arrayList.add(new AddTextToBufferBrick("myText", "Map"));
                arrayList.add(new SetTextBufferOnlyBrick("myText", 1));
                arrayList.add(new SetBufferCameraBrick("Map", "100", "200", SchemaSymbols.ATTVAL_TRUE_1, "0"));
                arrayList.add(new SetBufferCamera3DBrick(new Formula("Map"), new Formula((Integer) 100), new Formula((Integer) 100), new Formula((Integer) 100), new Formula((Integer) 120), new Formula((Integer) (-20)), new Formula((Integer) 0), new Formula((Integer) 67)));
                arrayList.add(new ApplyBufferLookBrick("Map"));
                arrayList.add(new BufferMaskBrick("Map", 1));
                arrayList.add(new SetBufferShaderBrick("Map", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nuniform sampler2D u_texture;\n\nvoid main() {\n    vec4 texColor = texture2D(u_texture, v_texCoords);\n    \n    texColor.rgb = 1.0 - texColor.rgb;\n    \n    gl_FragColor = v_color * texColor;\n}"));
                arrayList.add(new SetBufferShaderUniformBrick("Map", "time", 1.0f, 0.0f, 0.0f, 1));
                arrayList.add(new SaveBufferBrick(new Formula("Map"), new Formula("buffer.png")));
                arrayList.add(new StartBufferRecordingBrick("myBuffer", "output.mp4", 30, 2000000));
                arrayList.add(new StopBufferRecordingBrick());
                arrayList.add(new SetMainRenderLoopsBrick(1.0f, 1.0f, 1.0f));
                arrayList.add(new SetBufferEffectsBrick("Map", 1.0f, 1.0f));
                arrayList.add(new OpenUrlBrick(BrickValues.OPEN_IN_BROWSER));
                arrayList.add(new OpenAppBrick(new Formula("com.android.settings")));
                return arrayList;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ShowBrick showBrick = new ShowBrick();
        String string4 = context.getString(R.string.subcategory_looks_costumes);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        ShowBrick showBrick2 = showBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string4, showBrick2));
        if (!isBackgroundSprite) {
            arrayList2.add(new SetLookBrick());
            arrayList2.add(new SetLookByIndexBrick(1));
            arrayList2.add(new NextLookBrick());
            arrayList2.add(new PreviousLookBrick());
        }
        arrayList2.add(new PlayGifBrick("cat.gif"));
        arrayList2.add(new StopGifBrick());
        arrayList2.add(new PlaySpritesheetBrick(new Formula((Integer) 4), new Formula((Integer) 8), new Formula((Integer) 1), new Formula((Integer) 8), new Formula(Double.valueOf(0.1d))));
        arrayList2.add(new StopSpritesheetBrick());
        arrayList2.add(new WhenBackgroundChangesBrick());
        arrayList2.add(new SetBackgroundBrick());
        arrayList2.add(new SetBackgroundByIndexBrick(1));
        arrayList2.add(new SetBackgroundAndWaitBrick());
        arrayList2.add(new SetBackgroundByIndexAndWaitBrick(1));
        String string5 = context.getString(R.string.brick_paint_new_look_name);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        arrayList2.add(new PaintNewLookBrick(string5));
        arrayList2.add(new EditLookBrick());
        String string6 = context.getString(R.string.brick_copy_look_name);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        arrayList2.add(new CopyLookBrick(string6));
        arrayList2.add(new DeleteLookBrick());
        String string7 = context.getString(R.string.subcategory_looks_visibility);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string7, showBrick2));
        arrayList2.add(new ShowBrick());
        arrayList2.add(new HideBrick());
        arrayList2.add(new SetSizeToBrick(60.0d));
        arrayList2.add(new ChangeSizeByNBrick(10.0d));
        arrayList2.add(new SetWidthBrick(60.0d));
        arrayList2.add(new SetHeightBrick(60.0d));
        arrayList2.add(new ChangeWidthBrick(10.0d));
        arrayList2.add(new ChangeHeightBrick(10.0d));
        String string8 = context.getString(R.string.subcategory_looks_effects);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string8, showBrick2));
        arrayList2.add(new EasePropertyBrick(0, 0, 100.0f, 250.0f, 1.0f));
        arrayList2.add(new SetTransparencyBrick(50.0d));
        arrayList2.add(new ChangeTransparencyByNBrick(25.0d));
        arrayList2.add(new SetBrightnessBrick(50.0d));
        arrayList2.add(new ChangeBrightnessByNBrick(25.0d));
        arrayList2.add(new SetColorBrick(0.0d));
        arrayList2.add(new ChangeColorByNBrick(25.0d));
        arrayList2.add(new SetCornerOffsetsBrick(new Formula((Integer) 0), new Formula((Integer) 0), new Formula((Integer) 50), new Formula((Integer) 100), new Formula((Integer) 0), new Formula((Integer) 0), new Formula((Integer) 0), new Formula((Integer) 0)));
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        int i4 = 0;
        int i5 = 1;
        arrayList2.add(new FadeParticleEffectBrick(i4, i5, defaultConstructorMarker3));
        arrayList2.add(new ParticleEffectAdditivityBrick(i4, i5, defaultConstructorMarker3));
        arrayList2.add(new SetParticleColorBrick("#ff0000"));
        arrayList2.add(new ClearGraphicEffectBrick());
        String string9 = context.getString(R.string.subcategory_looks_dialogs);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string9, showBrick2));
        if (!isBackgroundSprite) {
            arrayList2.add(new SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)));
            arrayList2.add(new SayForBubbleBrick(context.getString(R.string.brick_say_bubble_default_value), 1.0f));
            arrayList2.add(new ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)));
            arrayList2.add(new ThinkForBubbleBrick(context.getString(R.string.brick_think_bubble_default_value), 1.0f));
        }
        arrayList2.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        arrayList2.add(new BigAskBrick(context.getString(R.string.brick_ask_default_question), "Введите ответ:", "OK", "Отмена", "Ваш ответ"));
        arrayList2.add(new ShowTextBrick(100, 200));
        arrayList2.add(new ShowTextColorSizeAlignmentBrick(100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
        arrayList2.add(new CreateDialogBrick("myDialog", "Фижма", "введите ваш ответ"));
        arrayList2.add(new SetPositiveBrick("myDialog", "Да"));
        arrayList2.add(new SetNeutralBrick("myDialog", "Позже"));
        arrayList2.add(new SetNegativeBrick("myDialog", "Нет"));
        arrayList2.add(new AddEditBrick("myDialog", "это текстовое поле"));
        arrayList2.add(new AddRadioBrick("myDialog", "это выбор"));
        arrayList2.add(new SetCallbackBrick("myDialog"));
        arrayList2.add(new ShowDialogBrick("myDialog"));
        String string10 = context.getString(R.string.subcategory_looks_camera2d);
        Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string10, showBrick2));
        arrayList2.add(new SetCameraFocusPointBrick());
        arrayList2.add(new SetCameraPosition2Brick(100, 300));
        arrayList2.add(new SetCameraRotation2Brick(90.0d));
        arrayList2.add(new SetCameraZoomBrick(3.0d));
        arrayList2.add(new PinToCameraBrick());
        arrayList2.add(new UnpinFromCameraBrick());
        String string11 = context.getString(R.string.subcategory_looks_camera_device);
        Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string11, showBrick2));
        if (!ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            DefaultConstructorMarker defaultConstructorMarker4 = null;
            boolean z2 = false;
            int i6 = 1;
            arrayList2.add(new CameraBrick(z2, i6, defaultConstructorMarker4));
            arrayList2.add(new ChooseCameraBrick(z2, i6, defaultConstructorMarker4));
            arrayList2.add(new FlashBrick());
        }
        if (!isBackgroundSprite) {
            arrayList2.add(new LookRequestBrick(BrickValues.LOOK_REQUEST));
        } else if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
            arrayList2.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE));
        } else {
            arrayList2.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST));
        }
        arrayList2.add(new ScreenShotBrick());
        arrayList2.add(new PhotoBrick());
        arrayList2.add(new CutLookBrick((Integer) 100, (Integer) 200, (Integer) 300, (Integer) 400));
        arrayList2.add(new SaveLookBrick("my_actor.png"));
        arrayList2.add(new LookFileBrick("my_actor.png"));
        arrayList2.add(new SaveLookFilesBrick("look.png"));
        arrayList2.add(new SetLookFilesBrick("look.png"));
        arrayList2.add(new LookToTableBrick("rTable", "gTable", "bTable", "aTable"));
        arrayList2.add(new LookFromTableBrick("rTable", "gTable", "bTable", "aTable"));
        String string12 = context.getString(R.string.subcategory_looks_primitives);
        Intrinsics.checkNotNullExpressionValue(string12, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string12, showBrick2));
        arrayList2.add(new SquareBrick("square", "#ff0000", 0.0f, 0.0f, 100.0f, 100.0f, 1.0f, 0.0f, 0.0f));
        arrayList2.add(new DelSquareBrick("square"));
        arrayList2.add(new OpenUrlBrick(BrickValues.OPEN_IN_BROWSER));
        arrayList2.add(new OpenAppBrick(new Formula("com.android.settings")));
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            arrayList2.add(new PhiroRGBLightBrick(PhiroRGBLightBrick.Eye.BOTH, 0, 255, 255));
        }
        String string13 = context.getString(R.string.subcategory_looks_buffers);
        Intrinsics.checkNotNullExpressionValue(string13, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string13, showBrick2));
        arrayList2.add(new CreateBufferBrick("Map", "512", "512"));
        arrayList2.add(new SetBufferModeBrick(new Formula("Map"), new Formula((Integer) 1), new Formula((Integer) 0)));
        arrayList2.add(new SetBufferAutoUpdateBrick(new Formula("Map"), new Formula((Integer) 1)));
        arrayList2.add(new AddToBufferBrick("Map"));
        arrayList2.add(new RemoveFromBufferBrick(new Formula("Map")));
        arrayList2.add(new SetBufferOnlyBrick(new Formula((Integer) 1)));
        arrayList2.add(new AddTextToBufferBrick("myText", "Map"));
        arrayList2.add(new SetTextBufferOnlyBrick("myText", 1));
        arrayList2.add(new SetBufferCameraBrick("Map", "100", "200", SchemaSymbols.ATTVAL_TRUE_1, "0"));
        arrayList2.add(new SetBufferCamera3DBrick(new Formula("Map"), new Formula((Integer) 100), new Formula((Integer) 100), new Formula((Integer) 100), new Formula((Integer) 120), new Formula((Integer) (-20)), new Formula((Integer) 0), new Formula((Integer) 67)));
        arrayList2.add(new ApplyBufferLookBrick("Map"));
        arrayList2.add(new BufferMaskBrick("Map", 1));
        arrayList2.add(new SetBufferShaderBrick("Map", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nuniform sampler2D u_texture;\n\nvoid main() {\n    vec4 texColor = texture2D(u_texture, v_texCoords);\n    \n    texColor.rgb = 1.0 - texColor.rgb;\n    \n    gl_FragColor = v_color * texColor;\n}"));
        arrayList2.add(new SetBufferShaderUniformBrick("Map", "time", 1.0f, 0.0f, 0.0f, 1));
        arrayList2.add(new SaveBufferBrick(new Formula("Map"), new Formula("buffer.png")));
        arrayList2.add(new StartBufferRecordingBrick("myBuffer", "output.mp4", 30, 2000000));
        arrayList2.add(new StopBufferRecordingBrick());
        arrayList2.add(new SetMainRenderLoopsBrick(1.0f, 1.0f, 1.0f));
        arrayList2.add(new SetBufferEffectsBrick("Map", 1.0f, 1.0f));
        return arrayList2;
    }

    private final List<Brick> setupPenCategoryList(boolean isBackgroundSprite) {
        ArrayList arrayList = new ArrayList();
        if (!isBackgroundSprite) {
            arrayList.add(new PenDownBrick());
            arrayList.add(new PenUpBrick());
            arrayList.add(new SetPenSizeBrick(3.15d));
            arrayList.add(new SetPenColorBrick(0, 0, 255));
            arrayList.add(new StampBrick());
        }
        arrayList.add(new ClearBackgroundBrick());
        return arrayList;
    }

    protected List<Brick> setupDataCategoryList(Context context, boolean isBackgroundSprite) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_data);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new SetVariableBrick(1.0d));
                arrayList.add(new ChangeVariableBrick(1.0d));
                arrayList.add(new SetVariableEasingBrick(1, 0.0f, 10.0f, 0.0f, 100.0f));
                arrayList.add(new ShowTextBrick(100, 200));
                arrayList.add(new ShowTextColorSizeAlignmentBrick(100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
                arrayList.add(new ShowVarFontBrick(100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR, "font.ttf"));
                arrayList.add(new ShowText3Brick("myText", "Hello!", 100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
                arrayList.add(new ShowTextFontBrick("myText", "Ababuy!", "font.ttf", 100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
                arrayList.add(new ShowTextRotationBrick("myText", "Ababuy!", 90.0f, "font.ttf", 100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
                arrayList.add(new HideTextBrick());
                arrayList.add(new HideText3Brick("myText"));
                arrayList.add(new WriteVariableOnDeviceBrick());
                arrayList.add(new ReadVariableFromDeviceBrick());
                String string2 = context.getString(R.string.brick_write_variable_to_file_default_value);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                arrayList.add(new WriteVariableToFileBrick(string2));
                String string3 = context.getString(R.string.brick_write_variable_to_file_default_value);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                arrayList.add(new ReadVariableFromFileBrick(string3));
                arrayList.add(new WriteToFilesBrick("variable.txt"));
                arrayList.add(new ReadFromFilesBrick("variable.txt"));
                arrayList.add(new DeleteFilesBrick("variable.txt"));
                arrayList.add(new FileUrlBrick("http://e95814zx.beget.tech/map.jpg", "fileFromUrl.jpg"));
                arrayList.add(new FilesUrlBrick("http://e95814zx.beget.tech/map.jpg", "fileFromUrl.jpg"));
                arrayList.add(new ZipProjectFilesBrick("file1.txt, file2.png", "archive.zip"));
                arrayList.add(new UnzipProjectFilesBrick("archive.zip"));
                arrayList.add(new GetZipFileNamesBrick("myZip.zip"));
                arrayList.add(new ZipBrick("myZip.zip", "my_actor.png,fileFromUrl.jpg"));
                arrayList.add(new UnzipBrick("myZip.zip"));
                arrayList.add(new AddItemToUserListBrick(1.0d));
                arrayList.add(new DeleteItemOfUserListBrick((Integer) 1));
                arrayList.add(new ClearUserListBrick());
                arrayList.add(new InsertItemIntoUserListBrick(1.0d, (Integer) 1));
                arrayList.add(new ReplaceItemInUserListBrick(1.0d, (Integer) 1));
                arrayList.add(new WriteListOnDeviceBrick());
                arrayList.add(new ReadListFromDeviceBrick());
                arrayList.add(new StoreCSVIntoUserListBrick((Integer) 1, context.getString(R.string.brick_store_csv_into_userlist_data)));
                arrayList.add(new SplitBrick(context.getString(R.string.brick_store_csv_into_userlist_data), ","));
                arrayList.add(new StringToTableBrick("1,2,3\n4,5,6\n7,8,9", ",", "\n", "myTable"));
                arrayList.add(new RegexBrick("panda ananas", "a[^n]+"));
                String string4 = context.getString(R.string.brick_web_request_default_value);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                arrayList.add(new WebRequestBrick(string4));
                arrayList.add(new PostWebRequestBrick("https://api.calfire.com/v2/texts?limit=50&offset=200", "Content-Type:application/json", "{\nusername=password\n}"));
                arrayList.add(new CreateVarBrick("variable1", "0"));
                arrayList.add(new DeleteVarBrick("variable1"));
                arrayList.add(new DeleteVarsBrick());
                arrayList.add(new CreateTableBrick("myTable", (Integer) 5, (Integer) 5));
                arrayList.add(new InsertTableBrick("myTable", SchemaSymbols.ATTVAL_TRUE_1, (Integer) 3, (Integer) 2));
                arrayList.add(new DeleteTableBrick("myTable"));
                arrayList.add(new DeleteAllTablesBrick());
                arrayList.add(new ShowToastBlock("Hello World!"));
                arrayList.add(new CopyTextBrick("Котлета"));
                arrayList.add(new ListenMicroBrick("100"));
                arrayList.add(new SquareBrick("square", "#ff0000", 0.0f, 0.0f, 100.0f, 100.0f, 1.0f, 0.0f, 0.0f));
                arrayList.add(new DelSquareBrick("square"));
                if (!isBackgroundSprite) {
                    arrayList.add(new LookRequestBrick(BrickValues.LOOK_REQUEST));
                } else if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
                    arrayList.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE));
                } else {
                    arrayList.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST));
                }
                arrayList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
                if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
                    arrayList.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
                }
                if (SettingsFragment.isEmroiderySharedPreferenceEnabled(context)) {
                    String string5 = context.getString(R.string.brick_default_embroidery_file);
                    Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
                    arrayList.add(new WriteEmbroideryToFileBrick(string5));
                }
                if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
                    arrayList.add(new StartListeningBrick());
                }
                if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
                    arrayList.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
                }
                return arrayList;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        DeleteVarsBrick deleteVarsBrick = new DeleteVarsBrick();
        String string6 = context.getString(R.string.subcategory_data_variables);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        DeleteVarsBrick deleteVarsBrick2 = deleteVarsBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string6, deleteVarsBrick2));
        arrayList2.add(new SetVariableBrick(1.0d));
        arrayList2.add(new ChangeVariableBrick(1.0d));
        arrayList2.add(new SetVariableEasingBrick(1, 0.0f, 10.0f, 0.0f, 100.0f));
        arrayList2.add(new CreateVarBrick("variable1", "0"));
        arrayList2.add(new DeleteVarBrick("variable1"));
        arrayList2.add(new DeleteVarsBrick());
        String string7 = context.getString(R.string.subcategory_data_lists);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string7, deleteVarsBrick2));
        arrayList2.add(new AddItemToUserListBrick(1.0d));
        arrayList2.add(new DeleteItemOfUserListBrick((Integer) 1));
        arrayList2.add(new InsertItemIntoUserListBrick(1.0d, (Integer) 1));
        arrayList2.add(new ReplaceItemInUserListBrick(1.0d, (Integer) 1));
        arrayList2.add(new ClearUserListBrick());
        arrayList2.add(new CreateTableBrick("myTable", (Integer) 5, (Integer) 5));
        arrayList2.add(new InsertTableBrick("myTable", SchemaSymbols.ATTVAL_TRUE_1, (Integer) 3, (Integer) 2));
        arrayList2.add(new DeleteTableBrick("myTable"));
        arrayList2.add(new DeleteAllTablesBrick());
        String string8 = context.getString(R.string.subcategory_data_visuals);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string8, deleteVarsBrick2));
        arrayList2.add(new ShowTextBrick(100, 200));
        arrayList2.add(new ShowTextColorSizeAlignmentBrick(100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
        arrayList2.add(new ShowVarFontBrick(100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR, "font.ttf"));
        arrayList2.add(new ShowText3Brick("myText", "Hello!", 100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
        arrayList2.add(new ShowTextFontBrick("myText", "Ababuy!", "font.ttf", 100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
        arrayList2.add(new ShowTextRotationBrick("myText", "Ababuy!", 90.0f, "font.ttf", 100, 200, 120.0d, BrickValues.SHOW_VARIABLE_COLOR));
        arrayList2.add(new HideTextBrick());
        arrayList2.add(new HideText3Brick("myText"));
        arrayList2.add(new ShowToastBlock("Hello World!"));
        arrayList2.add(new SquareBrick("square", "#ff0000", 0.0f, 0.0f, 100.0f, 100.0f, 1.0f, 0.0f, 0.0f));
        arrayList2.add(new DelSquareBrick("square"));
        String string9 = context.getString(R.string.subcategory_data_processing);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string9, deleteVarsBrick2));
        arrayList2.add(new StoreCSVIntoUserListBrick((Integer) 1, context.getString(R.string.brick_store_csv_into_userlist_data)));
        arrayList2.add(new SplitBrick(context.getString(R.string.brick_store_csv_into_userlist_data), ","));
        arrayList2.add(new StringToTableBrick("1,2,3\n4,5,6\n7,8,9", ",", "\n", "myTable"));
        arrayList2.add(new RegexBrick("panda ananas", "a[^n]+"));
        arrayList2.add(new CopyTextBrick("Котлета"));
        String string10 = context.getString(R.string.subcategory_data_files);
        Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string10, deleteVarsBrick2));
        arrayList2.add(new WriteVariableOnDeviceBrick());
        arrayList2.add(new ReadVariableFromDeviceBrick());
        String string11 = context.getString(R.string.brick_write_variable_to_file_default_value);
        Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
        arrayList2.add(new WriteVariableToFileBrick(string11));
        String string12 = context.getString(R.string.brick_write_variable_to_file_default_value);
        Intrinsics.checkNotNullExpressionValue(string12, "getString(...)");
        arrayList2.add(new ReadVariableFromFileBrick(string12));
        arrayList2.add(new WriteToFilesBrick("variable.txt"));
        arrayList2.add(new ReadFromFilesBrick("variable.txt"));
        arrayList2.add(new DeleteFilesBrick("variable.txt"));
        arrayList2.add(new WriteListOnDeviceBrick());
        arrayList2.add(new ReadListFromDeviceBrick());
        arrayList2.add(new ZipProjectFilesBrick("file1.txt, file2.png", "archive.zip"));
        arrayList2.add(new UnzipProjectFilesBrick("archive.zip"));
        arrayList2.add(new GetZipFileNamesBrick("myZip.zip"));
        arrayList2.add(new ZipBrick("myZip.zip", "my_actor.png,fileFromUrl.jpg"));
        arrayList2.add(new UnzipBrick("myZip.zip"));
        String string13 = context.getString(R.string.subcategory_data_web);
        Intrinsics.checkNotNullExpressionValue(string13, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string13, deleteVarsBrick2));
        String string14 = context.getString(R.string.brick_web_request_default_value);
        Intrinsics.checkNotNullExpressionValue(string14, "getString(...)");
        arrayList2.add(new WebRequestBrick(string14));
        arrayList2.add(new PostWebRequestBrick("https://api.calfire.com/v2/texts?limit=50&offset=200", "Content-Type:application/json", "{\nusername=password\n}"));
        arrayList2.add(new FileUrlBrick("http://e95814zx.beget.tech/map.jpg", "fileFromUrl.jpg"));
        arrayList2.add(new FilesUrlBrick("http://e95814zx.beget.tech/map.jpg", "fileFromUrl.jpg"));
        if (!isBackgroundSprite) {
            arrayList2.add(new LookRequestBrick(BrickValues.LOOK_REQUEST));
        } else if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
            arrayList2.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE));
        } else {
            arrayList2.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST));
        }
        arrayList2.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        arrayList2.add(new ListenMicroBrick("100"));
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            arrayList2.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
            arrayList2.add(new StartListeningBrick());
        }
        if (SettingsFragment.isEmroiderySharedPreferenceEnabled(context)) {
            String string15 = context.getString(R.string.brick_default_embroidery_file);
            Intrinsics.checkNotNullExpressionValue(string15, "getString(...)");
            arrayList2.add(new WriteEmbroideryToFileBrick(string15));
        }
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            arrayList2.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
        }
        return arrayList2;
    }

    protected final List<Brick> setupDeviceCategoryList(Context context, boolean isBackgroundSprite) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(R.string.category_device);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new ShowToastBlock("Hello World"));
                arrayList.add(new CopyTextBrick("Котлета"));
                arrayList.add(new ShowNotificationBrick("123456", "NewCatroid", "Привееет", "Это уведомление (точнее, его текст)", "icon.png"));
                arrayList.add(new NotificationCreateBrick("123456", "NewCatroid", "Привееет опять", "Это уже сложное уведомление", "icon.png"));
                arrayList.add(new NotificationAddButtonBrick("123456", "123", "CLICK ME", "none.png", "Enter any text..."));
                arrayList.add(new NotificationShowBrick("123456", 60.0d));
                arrayList.add(new NotificationCancelBrick("123456"));
                arrayList.add(new EnableBackgroundModeBrick("Приложение работает в фоне", "без уведомления в Android 14+ нельзя к сожалению("));
                arrayList.add(new DisableBackgroundModeBrick());
                arrayList.add(new ListenMicroBrick("100"));
                arrayList.add(new RunJSBrick("1 + 2"));
                arrayList.add(new RunLuaBrick("return 'Привет из Lua!'"));
                arrayList.add(new LunoScriptBrick("MakeToast(\"Hey from Luno! :)\");"));
                arrayList.add(new JavaCompileBrick(new Formula("Main.java"), new Formula(DexFormat.DEX_IN_JAR_NAME), null));
                arrayList.add(new JavaLoadAndRunBrick(new Formula("classes.dex, libs.jar"), new Formula("game.Main"), new Formula("onStart"), new Formula("Hello from NewCatroid!")));
                arrayList.add(new JavaDownloadDepBrick(new Formula("org.jsoup:jsoup:1.13.1"), new Formula(""), null, true));
                arrayList.add(new ClearPythonEnvironmentBrick());
                arrayList.add(new LoadNativeModuleBrick("libz.so"));
                arrayList.add(new LoadPythonLibraryBrick("telebot.whl"));
                arrayList.add(new RunPythonScriptBrick("import telebot\n\nAPI_TOKEN = 'YOUR_API_TOKEN' #Telegram Bot API\n\nbot = telebot.TeleBot(API_TOKEN)\n\n@bot.message_handler(commands=['start'])\ndef send_welcome(message):\n    bot.reply_to(message, \"Welcome! I am a simple test bot.\")\n\n@bot.message_handler(commands=['hello'])\ndef send_hello(message):\n    bot.reply_to(message, \"Hello there!\")\n\nprint(\"Bot is starting...\")\nbot.polling()\nprint(\"Bot has stopped.\")", "myVar"));
                arrayList.add(new RunShellBrick("pip install --file pyTelegramBotApi"));
                arrayList.add(new OpenFileBrick("fileFromUrl.txt"));
                arrayList.add(new MoveFilesBrick("variable.txt"));
                arrayList.add(new MoveDownloadsBrick("variable.txt"));
                arrayList.add(new CopyProjectFileBrick("variable.txt", "copy_variable.txt"));
                arrayList.add(new OrientationBrick());
                arrayList.add(new CreateWebUrlBrick("myWebView", "https://google.com", "0", "0", "500", "700"));
                arrayList.add(new CreateWebFileBrick("myWebView", "<html><body style='background-color:lightyellow;'><h1>Привет, мир!</h1><p>Это WebView, созданный прямо из кода.</p><button onclick='alert(\"JavaScript работает!\")'>Нажми меня</button></body></html>", "0", "0", "500", "700"));
                arrayList.add(new SetWebBrick("myWebView"));
                arrayList.add(new EvalWebBrick("Android.postMessage(\"Hello from WebView!\");", "myWebView"));
                arrayList.add(new CreateVideoBrick("myVideoPlayer", "video.mp4", (Integer) 0, (Integer) 0, (Integer) 750, (Integer) 500, (Integer) 1, (Integer) 0));
                arrayList.add(new PlayVideoBrick("myVideoPlayer"));
                arrayList.add(new PauseVideoBrick("myVideoPlayer"));
                arrayList.add(new SeekVideoBrick("myVideoPlayer", (Integer) 30));
                arrayList.add(new CreateTextFieldBrick("myTextField", "", (Integer) 300, (Integer) 500, (Integer) 300, (Integer) 200, (Integer) 22, "#FFFFFF", "#88000000", "Напишите значение...", "#CCCCCC", "left", (Integer) 0, (Integer) 5, (Integer) (-1), "text", "если файла не существует - по умолчанию"));
                arrayList.add(new AttachSOBrick("glView", "mylib.so"));
                arrayList.add(new CreateGLViewBrick("glView", 100, 200, 500, 300));
                arrayList.add(new SetViewPositionBrick("myVideoPlayer", 100, 200));
                arrayList.add(new SetNativeParentBrick("myTextView", "myScrollView"));
                arrayList.add(new CreateScrollViewBrick("myScrollView", 1, "#22000000", true, 16, 1, 100, 200, 500, 600));
                arrayList.add(new CreateTextLabelBrick("myTextView", "<b>Привет</b> Это HTML текст.", "#FFFFFF", 18.0d, "#44000000", "fonts/myfont.ttf", 8, 1.2d, 1, 1, 0, 150, 250, 400, 150));
                arrayList.add(new CreateButtonBrick("myButton", "Нажми меня!", "#FFFFFF", 18.0d, "#00C853", 12, 100, 300, BrickValues.DRONE_ROTATION_MAX, 100));
                arrayList.add(new CreateSwitchBrick("mySwitch", "Включить", 1, 100, ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE, 400, 80));
                arrayList.add(new CreateImageViewBrick("myImage", "https://image.pollinations.ai/prompt/cat", 16, 1, 100, 100, 300, 300));
                arrayList.add(new CreateSliderBrick("mySlider", 0.0d, 1.0d, 0.5d, 100, 420, 500, 60));
                arrayList.add(new NativeViewConfigBrick("myWebView", 1, Float.valueOf(0.5f)));
                arrayList.add(new NativeViewControlBrick("myWebView", 5, "https://google.com"));
                arrayList.add(new NativeViewListenerBrick("myWebView", 0, null));
                arrayList.add(new NativeViewAnimateBrick("myButton", 0, 1.0d, 500, 29));
                arrayList.add(new NativeViewBindSpriteBrick("myWebView", "Sprite1", 1, 50, 100));
                arrayList.add(new DeleteWebBrick("myWebView"));
                arrayList.add(new BindVmOutputBrick());
                arrayList.add(new RunVm2Brick("-kernel \"%PROJECT_FILES%/bzImage\" -initrd \"%PROJECT_FILES%/core.gz\" -append \"console=ttyS0 quiet\""));
                arrayList.add(new RunVMBrick("1024", "2", "myDisk.qcow2", "flash.iso"));
                arrayList.add(new CreateDiskBrick("myDisk.qcow2", "10G"));
                arrayList.add(new ToggleDisplayBrick(1));
                arrayList.add(new VmSetMonitorSizeBrick(1000, ParserBasicInformation.NUM_RULES));
                arrayList.add(new MouseEventBrick("0", "100", 1));
                arrayList.add(new VmRelativeMouseMoveBrick(0, 100, 1));
                arrayList.add(new KeyEventBrick("a", 1));
                arrayList.add(new SendVmInputBrick("ls ~/"));
                arrayList.add(new StopVMBrick());
                arrayList.add(new RunChip8Brick("tetris.ch8"));
                arrayList.add(new ScreenShotBrick());
                arrayList.add(new ResetTimerBrick());
                arrayList.add(new TestBrick());
                arrayList.add(new HideStatusBarBrick());
                arrayList.add(new ChooseFileBrick());
                arrayList.add(new ExportProjectFileBrick("file.txt"));
                arrayList.add(new ShareBrick("Привет из NewCatroid!"));
                arrayList.add(new SaveToInternalStorageBrick("file.txt", "myAwesomeApp/file.txt"));
                arrayList.add(new LoadFromInternalStorageBrick("myAwesomeApp/file.txt"));
                arrayList.add(new ApplyShaderToImageBrick("image.png", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main() {\n    vec4 original = texture2D(u_texture, v_texCoords);\n    float roughness = original.r;\n    float metallic = 0.0;\n    gl_FragColor = vec4(1.0, roughness, metallic, 1.0);\n}"));
                arrayList.add(new WhenBrick());
                arrayList.add(new WhenTouchDownBrick());
                if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
                    arrayList.add(new WhenNfcBrick());
                    arrayList.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
                }
                String string2 = context.getString(R.string.brick_web_request_default_value);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                arrayList.add(new WebRequestBrick(string2));
                if (!isBackgroundSprite) {
                    arrayList.add(new LookRequestBrick(BrickValues.LOOK_REQUEST));
                } else if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
                    arrayList.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE));
                } else {
                    arrayList.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST));
                }
                arrayList.add(new OpenUrlBrick(BrickValues.OPEN_IN_BROWSER));
                arrayList.add(new OpenAppBrick(new Formula("com.android.settings")));
                arrayList.add(new VibrationBrick(1.0d));
                if (SettingsFragment.isAISpeechSynthetizationSharedPreferenceEnabled(context)) {
                    arrayList.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
                    arrayList.add(new SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)));
                }
                if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
                    arrayList.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
                    arrayList.add(new StartListeningBrick());
                }
                if (ProjectManager.getInstance().getCurrentProject() != null && !ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                    DefaultConstructorMarker defaultConstructorMarker = null;
                    boolean z = false;
                    int i = 1;
                    arrayList.add(new CameraBrick(z, i, defaultConstructorMarker));
                    arrayList.add(new ChooseCameraBrick(z, i, defaultConstructorMarker));
                    arrayList.add(new FlashBrick());
                }
                arrayList.add(new WriteVariableOnDeviceBrick());
                arrayList.add(new ReadVariableFromDeviceBrick());
                String string3 = context.getString(R.string.brick_write_variable_to_file_default_value);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                arrayList.add(new WriteVariableToFileBrick(string3));
                String string4 = context.getString(R.string.brick_write_variable_to_file_default_value);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                arrayList.add(new ReadVariableFromFileBrick(string4));
                arrayList.add(new WriteListOnDeviceBrick());
                arrayList.add(new ReadListFromDeviceBrick());
                arrayList.add(new TapAtBrick(-100, BrickValues.TOUCH_Y_START));
                arrayList.add(new TapForBrick(-100, BrickValues.TOUCH_Y_START, 0.3d));
                arrayList.add(new TouchAndSlideBrick(-100, BrickValues.TOUCH_Y_START, 100, 200, 0.3d));
                if (SettingsFragment.isCastSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupChromecastCategoryList(context));
                }
                if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupLegoNxtCategoryList());
                }
                if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupLegoEv3CategoryList());
                }
                if (SettingsFragment.isDroneSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupDroneCategoryList());
                }
                if (SettingsFragment.isJSSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupJumpingSumoCategoryList());
                }
                if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupPhiroProCategoryList());
                }
                if (SettingsFragment.isArduinoSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupArduinoCategoryList());
                }
                if (SettingsFragment.isRaspiSharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupRaspiCategoryList());
                }
                if (SettingsFragment.isEmroiderySharedPreferenceEnabled(context)) {
                    arrayList.addAll(setupEmbroideryCategoryList(context));
                }
                return arrayList;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ResetTimerBrick resetTimerBrick = new ResetTimerBrick();
        String string5 = context.getString(R.string.subcategory_device_scripting);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        ResetTimerBrick resetTimerBrick2 = resetTimerBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string5, resetTimerBrick2));
        arrayList2.add(new RunJSBrick("1 + 2"));
        arrayList2.add(new RunLuaBrick("return 'Привет из Lua!'"));
        arrayList2.add(new LunoScriptBrick("MakeToast(\"Hey from Luno! :)\");"));
        arrayList2.add(new JavaCompileBrick(new Formula("Main.java"), new Formula(DexFormat.DEX_IN_JAR_NAME), null));
        arrayList2.add(new JavaLoadAndRunBrick(new Formula("classes.dex, libs.jar"), new Formula("game.Main"), new Formula("onStart"), new Formula("Hello from NewCatroid!")));
        arrayList2.add(new JavaDownloadDepBrick(new Formula("org.jsoup:jsoup:1.13.1"), new Formula(""), null, true));
        arrayList2.add(new RunPythonScriptBrick("import telebot\nAPI_TOKEN = 'YOUR_API_TOKEN' #Telegram Bot API\nbot = telebot.TeleBot(API_TOKEN)\n@bot.message_handler(commands=['start'])\ndef send_welcome(message):\n    bot.reply_to(message, \"Welcome! I am a simple test bot.\")\nprint(\"Bot is starting...\")\nbot.polling()", "myVar"));
        arrayList2.add(new LoadNativeModuleBrick("libz.so"));
        arrayList2.add(new LoadPythonLibraryBrick("telebot.whl"));
        arrayList2.add(new ClearPythonEnvironmentBrick());
        arrayList2.add(new RunShellBrick("pip install --file pyTelegramBotApi"));
        String string6 = context.getString(R.string.subcategory_device_vm);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string6, resetTimerBrick2));
        arrayList2.add(new RunVMBrick("1024", "2", "myDisk.qcow2", "flash.iso"));
        arrayList2.add(new RunVm2Brick("-kernel \"%PROJECT_FILES%/bzImage\" -initrd \"%PROJECT_FILES%/core.gz\" -append \"console=ttyS0 quiet\""));
        arrayList2.add(new StopVMBrick());
        arrayList2.add(new CreateDiskBrick("myDisk.qcow2", "10G"));
        arrayList2.add(new ToggleDisplayBrick(1));
        arrayList2.add(new VmSetMonitorSizeBrick(1000, ParserBasicInformation.NUM_RULES));
        arrayList2.add(new MouseEventBrick("0", "100", 1));
        arrayList2.add(new VmRelativeMouseMoveBrick(0, 100, 1));
        arrayList2.add(new KeyEventBrick("a", 1));
        arrayList2.add(new SendVmInputBrick("ls ~/"));
        arrayList2.add(new BindVmOutputBrick());
        arrayList2.add(new RunChip8Brick("tetris.ch8"));
        String string7 = context.getString(R.string.subcategory_device_views);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string7, resetTimerBrick2));
        arrayList2.add(new CreateWebUrlBrick("myWebView", "https://google.com", "0", "0", "500", "700"));
        arrayList2.add(new CreateWebFileBrick("myWebView", "<html><body><h1>Привет!</h1></body></html>", "0", "0", "500", "700"));
        arrayList2.add(new SetWebBrick("myWebView"));
        arrayList2.add(new EvalWebBrick("Android.postMessage(\"Hello from WebView!\");", "myWebView"));
        arrayList2.add(new CreateVideoBrick("myVideoPlayer", "video.mp4", (Integer) 0, (Integer) 0, (Integer) 750, (Integer) 500, (Integer) 1, (Integer) 0));
        arrayList2.add(new PlayVideoBrick("myVideoPlayer"));
        arrayList2.add(new PauseVideoBrick("myVideoPlayer"));
        arrayList2.add(new SeekVideoBrick("myVideoPlayer", (Integer) 30));
        arrayList2.add(new CreateTextFieldBrick("myTextField", "", (Integer) 300, (Integer) 500, (Integer) 300, (Integer) 200, (Integer) 22, "#FFFFFF", "#88000000", "Напишите значение...", "#CCCCCC", "left", (Integer) 0, (Integer) 5, (Integer) (-1), "text", "если файла не существует - по умолчанию"));
        arrayList2.add(new AttachSOBrick("glView", "mylib.so"));
        arrayList2.add(new CreateGLViewBrick("glView", 100, 200, 500, 300));
        arrayList2.add(new SetViewPositionBrick("myVideoPlayer", 100, 200));
        arrayList2.add(new SetNativeParentBrick("myTextView", "myScrollView"));
        arrayList2.add(new CreateScrollViewBrick("myScrollView", 1, "#22000000", true, 16, 1, 100, 200, 500, 600));
        arrayList2.add(new CreateTextLabelBrick("myTextView", "<b>Привет</b> Это HTML текст.", "#FFFFFF", 18.0d, "#44000000", "fonts/myfont.ttf", 8, 1.2d, 1, 1, 0, 150, 250, 400, 150));
        arrayList2.add(new CreateButtonBrick("myButton", "Нажми меня!", "#FFFFFF", 18.0d, "#00C853", 12, 100, 300, BrickValues.DRONE_ROTATION_MAX, 100));
        arrayList2.add(new CreateSwitchBrick("mySwitch", "Включить", 1, 100, ConstantPool.METHODS_AND_FIELDS_INITIAL_SIZE, 400, 80));
        arrayList2.add(new CreateImageViewBrick("myImage", "https://image.pollinations.ai/prompt/cat", 16, 1, 100, 100, 300, 300));
        arrayList2.add(new CreateSliderBrick("mySlider", 0.0d, 1.0d, 0.5d, 100, 420, 500, 60));
        arrayList2.add(new NativeViewConfigBrick("myWebView", 1, Float.valueOf(0.5f)));
        arrayList2.add(new NativeViewControlBrick("myWebView", 5, "https://google.com"));
        arrayList2.add(new NativeViewListenerBrick("myWebView", 0, null));
        arrayList2.add(new NativeViewAnimateBrick("myButton", 0, 1.0d, 500, 29));
        arrayList2.add(new NativeViewBindSpriteBrick("myWebView", "Sprite1", 1, 50, 100));
        arrayList2.add(new DeleteWebBrick("myWebView"));
        String string8 = context.getString(R.string.subcategory_device_storage);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string8, resetTimerBrick2));
        arrayList2.add(new OpenFileBrick("fileFromUrl.txt"));
        arrayList2.add(new ChooseFileBrick());
        arrayList2.add(new MoveFilesBrick("variable.txt"));
        arrayList2.add(new MoveDownloadsBrick("variable.txt"));
        arrayList2.add(new CopyProjectFileBrick("variable.txt", "copy_variable.txt"));
        arrayList2.add(new ExportProjectFileBrick("file.txt"));
        arrayList2.add(new ShareBrick("Привет из NewCatroid!"));
        arrayList2.add(new SaveToInternalStorageBrick("file.txt", "myAwesomeApp/file.txt"));
        arrayList2.add(new LoadFromInternalStorageBrick("myAwesomeApp/file.txt"));
        arrayList2.add(new WriteVariableOnDeviceBrick());
        arrayList2.add(new ReadVariableFromDeviceBrick());
        String string9 = context.getString(R.string.brick_write_variable_to_file_default_value);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        arrayList2.add(new WriteVariableToFileBrick(string9));
        String string10 = context.getString(R.string.brick_write_variable_to_file_default_value);
        Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
        arrayList2.add(new ReadVariableFromFileBrick(string10));
        arrayList2.add(new WriteListOnDeviceBrick());
        arrayList2.add(new ReadListFromDeviceBrick());
        String string11 = context.getString(R.string.subcategory_device_hardware);
        Intrinsics.checkNotNullExpressionValue(string11, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string11, resetTimerBrick2));
        arrayList2.add(new OrientationBrick());
        arrayList2.add(new VibrationBrick(1.0d));
        arrayList2.add(new ScreenShotBrick());
        arrayList2.add(new HideStatusBarBrick());
        if (ProjectManager.getInstance().getCurrentProject() != null && !ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            DefaultConstructorMarker defaultConstructorMarker2 = null;
            boolean z2 = false;
            int i2 = 1;
            arrayList2.add(new CameraBrick(z2, i2, defaultConstructorMarker2));
            arrayList2.add(new ChooseCameraBrick(z2, i2, defaultConstructorMarker2));
            arrayList2.add(new FlashBrick());
        }
        String string12 = context.getString(R.string.subcategory_device_speech);
        Intrinsics.checkNotNullExpressionValue(string12, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string12, resetTimerBrick2));
        arrayList2.add(new ShowToastBlock("Hello World"));
        arrayList2.add(new CopyTextBrick("Котлета"));
        arrayList2.add(new ShowNotificationBrick("123456", "NewCatroid", "Привееет", "Это уведомление (точнее, его текст)", "icon.png"));
        arrayList2.add(new NotificationCreateBrick("123456", "NewCatroid", "Привееет опять", "Это уже сложное уведомление", "icon.png"));
        arrayList2.add(new NotificationAddButtonBrick("123456", "123", "CLICK ME", "none.png", "Enter any text..."));
        arrayList2.add(new NotificationShowBrick("123456", 60.0d));
        arrayList2.add(new NotificationCancelBrick("123456"));
        arrayList2.add(new EnableBackgroundModeBrick("Приложение работает в фоне", "без уведомления в Android 14+ нельзя к сожалению("));
        arrayList2.add(new DisableBackgroundModeBrick());
        arrayList2.add(new ListenMicroBrick("100"));
        arrayList2.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        if (SettingsFragment.isAISpeechSynthetizationSharedPreferenceEnabled(context)) {
            arrayList2.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
            arrayList2.add(new SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)));
        }
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(context)) {
            arrayList2.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
            arrayList2.add(new StartListeningBrick());
        }
        String string13 = context.getString(R.string.subcategory_device_emulation);
        Intrinsics.checkNotNullExpressionValue(string13, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string13, resetTimerBrick2));
        arrayList2.add(new TapAtBrick(-100, BrickValues.TOUCH_Y_START));
        arrayList2.add(new TapForBrick(-100, BrickValues.TOUCH_Y_START, 0.3d));
        arrayList2.add(new TouchAndSlideBrick(-100, BrickValues.TOUCH_Y_START, 100, 200, 0.3d));
        String string14 = context.getString(R.string.brick_web_request_default_value);
        Intrinsics.checkNotNullExpressionValue(string14, "getString(...)");
        arrayList2.add(new WebRequestBrick(string14));
        arrayList2.add(new OpenUrlBrick(BrickValues.OPEN_IN_BROWSER));
        arrayList2.add(new OpenAppBrick(new Formula("com.android.settings")));
        if (!isBackgroundSprite) {
            arrayList2.add(new LookRequestBrick(BrickValues.LOOK_REQUEST));
        } else if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
            arrayList2.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST_LANDSCAPE));
        } else {
            arrayList2.add(new BackgroundRequestBrick(BrickValues.BACKGROUND_REQUEST));
        }
        String string15 = context.getString(R.string.subcategory_device_misc);
        Intrinsics.checkNotNullExpressionValue(string15, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string15, resetTimerBrick2));
        arrayList2.add(new ResetTimerBrick());
        arrayList2.add(new TestBrick());
        arrayList2.add(new ApplyShaderToImageBrick("image.png", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main() {\n    vec4 original = texture2D(u_texture, v_texCoords);\n    float roughness = original.r;\n    float metallic = 0.0;\n    gl_FragColor = vec4(1.0, roughness, metallic, 1.0);\n}"));
        arrayList2.add(new WhenBrick());
        arrayList2.add(new WhenTouchDownBrick());
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            arrayList2.add(new WhenNfcBrick());
            arrayList2.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
        }
        if (SettingsFragment.isCastSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupChromecastCategoryList(context));
        }
        if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupLegoNxtCategoryList());
        }
        if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupLegoEv3CategoryList());
        }
        if (SettingsFragment.isDroneSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupDroneCategoryList());
        }
        if (SettingsFragment.isJSSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupJumpingSumoCategoryList());
        }
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupPhiroProCategoryList());
        }
        if (SettingsFragment.isArduinoSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupArduinoCategoryList());
        }
        if (SettingsFragment.isRaspiSharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupRaspiCategoryList());
        }
        if (SettingsFragment.isEmroiderySharedPreferenceEnabled(context)) {
            arrayList2.addAll(setupEmbroideryCategoryList(context));
        }
        return arrayList2;
    }

    private final List<Brick> setupLegoNxtCategoryList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new LegoNxtMotorTurnAngleBrick(LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A, 180));
        arrayList.add(new LegoNxtMotorStopBrick(LegoNxtMotorStopBrick.Motor.MOTOR_A));
        arrayList.add(new LegoNxtMotorMoveBrick(LegoNxtMotorMoveBrick.Motor.MOTOR_A, 100));
        arrayList.add(new LegoNxtPlayToneBrick(2.0d, 1.0d));
        return arrayList;
    }

    private final List<Brick> setupLegoEv3CategoryList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new LegoEv3MotorTurnAngleBrick(LegoEv3MotorTurnAngleBrick.Motor.MOTOR_A, 180));
        arrayList.add(new LegoEv3MotorMoveBrick(LegoEv3MotorMoveBrick.Motor.MOTOR_A, 100));
        arrayList.add(new LegoEv3MotorStopBrick(LegoEv3MotorStopBrick.Motor.MOTOR_A));
        arrayList.add(new LegoEv3PlayToneBrick(2.0d, 1.0d, 100.0d));
        arrayList.add(new LegoEv3SetLedBrick(LegoEv3SetLedBrick.LedStatus.LED_GREEN));
        return arrayList;
    }

    private final List<Brick> setupDroneCategoryList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new DroneTakeOffLandBrick());
        arrayList.add(new DroneEmergencyBrick());
        arrayList.add(new DroneMoveUpBrick(1000, 20));
        arrayList.add(new DroneMoveDownBrick(1000, 20));
        arrayList.add(new DroneMoveLeftBrick(1000, 20));
        arrayList.add(new DroneMoveRightBrick(1000, 20));
        arrayList.add(new DroneMoveForwardBrick(1000, 20));
        arrayList.add(new DroneMoveBackwardBrick(1000, 20));
        arrayList.add(new DroneTurnLeftBrick(1000, 20));
        arrayList.add(new DroneTurnRightBrick(1000, 20));
        arrayList.add(new DroneFlipBrick());
        arrayList.add(new DronePlayLedAnimationBrick());
        arrayList.add(new DroneSwitchCameraBrick());
        return arrayList;
    }

    private final List<Brick> setupJumpingSumoCategoryList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new JumpingSumoMoveForwardBrick(1000, 80));
        arrayList.add(new JumpingSumoMoveBackwardBrick(1000, 80));
        arrayList.add(new JumpingSumoAnimationsBrick(JumpingSumoAnimationsBrick.Animation.SPIN));
        arrayList.add(new JumpingSumoSoundBrick(JumpingSumoSoundBrick.Sounds.DEFAULT, 50));
        arrayList.add(new JumpingSumoNoSoundBrick());
        arrayList.add(new JumpingSumoJumpLongBrick());
        arrayList.add(new JumpingSumoJumpHighBrick());
        arrayList.add(new JumpingSumoRotateLeftBrick(90.0d));
        arrayList.add(new JumpingSumoRotateRightBrick(90.0d));
        arrayList.add(new JumpingSumoTurnBrick());
        arrayList.add(new JumpingSumoTakingPictureBrick());
        return arrayList;
    }

    private final List<Brick> setupPhiroProCategoryList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PhiroMotorMoveForwardBrick(PhiroMotorMoveForwardBrick.Motor.MOTOR_LEFT, 100));
        arrayList.add(new PhiroMotorMoveBackwardBrick(PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT, 100));
        arrayList.add(new PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH));
        arrayList.add(new PhiroPlayToneBrick(PhiroPlayToneBrick.Tone.DO, 1));
        arrayList.add(new PhiroRGBLightBrick(PhiroRGBLightBrick.Eye.BOTH, 0, 255, 255));
        arrayList.add(new PhiroIfLogicBeginBrick());
        arrayList.add(new SetVariableBrick(Sensors.PHIRO_FRONT_LEFT));
        arrayList.add(new SetVariableBrick(Sensors.PHIRO_FRONT_RIGHT));
        arrayList.add(new SetVariableBrick(Sensors.PHIRO_SIDE_LEFT));
        arrayList.add(new SetVariableBrick(Sensors.PHIRO_SIDE_RIGHT));
        arrayList.add(new SetVariableBrick(Sensors.PHIRO_BOTTOM_LEFT));
        arrayList.add(new SetVariableBrick(Sensors.PHIRO_BOTTOM_RIGHT));
        return arrayList;
    }

    private final List<Brick> setupArduinoCategoryList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ArduinoSendDigitalValueBrick(13, 1));
        arrayList.add(new ArduinoSendPWMValueBrick(3, 255));
        return arrayList;
    }

    private final List<Brick> setupRaspiCategoryList() {
        RaspiInterruptScript raspiInterruptScript = new RaspiInterruptScript("3", "pressed");
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WhenRaspiPinChangedBrick(raspiInterruptScript));
        arrayList.add(new RaspiIfLogicBeginBrick(new Formula((Integer) 3)));
        arrayList.add(new RaspiSendDigitalValueBrick(3, 1));
        arrayList.add(new RaspiPwmBrick(3, 100.0d, 50.0d));
        return arrayList;
    }

    private final List<Brick> setupEmbroideryCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StitchBrick());
        arrayList.add(new SetThreadColorBrick(new Formula("#ff0000")));
        arrayList.add(new RunningStitchBrick(new Formula((Integer) 10)));
        arrayList.add(new ZigZagStitchBrick(new Formula((Integer) 2), new Formula((Integer) 10)));
        arrayList.add(new TripleStitchBrick(new Formula((Integer) 10)));
        arrayList.add(new SewUpBrick());
        arrayList.add(new StopRunningStitchBrick());
        String string = context.getString(R.string.brick_default_embroidery_file);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(new WriteEmbroideryToFileBrick(string));
        return arrayList;
    }

    private final List<Brick> setupPlotCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StartPlotBrick());
        arrayList.add(new StopPlotBrick());
        String string = context.getString(R.string.brick_default_plot_file);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(new SavePlotBrick(string));
        return arrayList;
    }

    private final List<Brick> setupNeuralCategoryList(Context context) {
        Integer num;
        String string = context.getString(R.string.category_neural);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String preferenceKeyForCategory = getPreferenceKeyForCategory(string, context);
        if (preferenceKeyForCategory != null) {
            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKeyForCategory, Intrinsics.areEqual(string, context.getString(R.string.category_threed)))) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new SetGeminiKeyBrick("api_key"));
                arrayList.add(new AskGeminiBrick("Hello!"));
                arrayList.add(new AskGemini2Brick("Hello! How are you?", "models/gemini-flash-lite-latest"));
                arrayList.add(new AskGPTBrick("Привет!", "Отвечай на все словом \"апельсин\""));
                arrayList.add(new CreateFloatBrick("FloatArray"));
                arrayList.add(new PutFloatBrick("FloatArray", (Integer) 1, (Integer) 0));
                arrayList.add(new TableToFloatBrick("myTable", "FloatArray"));
                arrayList.add(new DeleteFloatBrick("FloatArray", (Integer) 0));
                arrayList.add(new LoadNNBrick("model.onnx"));
                arrayList.add(new PredictNNBrick("FloatArray"));
                arrayList.add(new UnloadNNBrick());
                arrayList.add(new ResizeImgBrick("image.png", (Integer) 64, (Integer) 64));
                arrayList.add(new GrayscaleImgBrick("image.png"));
                arrayList.add(new NormalizeImgBrick("image.png", "rTable", "gTable", "bTable"));
                arrayList.add(new CutLookBrick((Integer) 100, (Integer) 200, (Integer) 300, (Integer) 400));
                arrayList.add(new ApplyShaderToImageBrick("image.png", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main() {\n    vec4 original = texture2D(u_texture, v_texCoords);\n    float roughness = original.r;\n    float metallic = 0.0;\n    gl_FragColor = vec4(1.0, roughness, metallic, 1.0);\n}"));
                return arrayList;
            }
            num = 64;
        } else {
            num = 64;
        }
        ArrayList arrayList2 = new ArrayList();
        UnloadNNBrick unloadNNBrick = new UnloadNNBrick();
        Integer num2 = num;
        String string2 = context.getString(R.string.subcategory_neural_llm);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        UnloadNNBrick unloadNNBrick2 = unloadNNBrick;
        arrayList2.add(new SubCategoryHeaderBrick(string2, unloadNNBrick2));
        arrayList2.add(new SetGeminiKeyBrick("api_key"));
        arrayList2.add(new AskGeminiBrick("Hello!"));
        arrayList2.add(new AskGemini2Brick("Hello! How are you?", "models/gemini-flash-lite-latest"));
        arrayList2.add(new AskGPTBrick("Привет!", "Отвечай на все словом \"апельсин\""));
        String string3 = context.getString(R.string.subcategory_neural_local);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string3, unloadNNBrick2));
        arrayList2.add(new LoadNNBrick("model.onnx"));
        arrayList2.add(new PredictNNBrick("FloatArray"));
        arrayList2.add(new UnloadNNBrick());
        String string4 = context.getString(R.string.subcategory_neural_tensors);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string4, unloadNNBrick2));
        arrayList2.add(new CreateFloatBrick("FloatArray"));
        arrayList2.add(new PutFloatBrick("FloatArray", (Integer) 1, (Integer) 0));
        arrayList2.add(new TableToFloatBrick("myTable", "FloatArray"));
        arrayList2.add(new DeleteFloatBrick("FloatArray", (Integer) 0));
        String string5 = context.getString(R.string.subcategory_neural_vision);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        arrayList2.add(new SubCategoryHeaderBrick(string5, unloadNNBrick2));
        arrayList2.add(new ResizeImgBrick("image.png", num2, num2));
        arrayList2.add(new GrayscaleImgBrick("image.png"));
        arrayList2.add(new NormalizeImgBrick("image.png", "rTable", "gTable", "bTable"));
        arrayList2.add(new CutLookBrick((Integer) 100, (Integer) 200, (Integer) 300, (Integer) 400));
        arrayList2.add(new ApplyShaderToImageBrick("image.png", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main() {\n    vec4 original = texture2D(u_texture, v_texCoords);\n    float roughness = original.r;\n    float metallic = 0.0;\n    gl_FragColor = vec4(1.0, roughness, metallic, 1.0);\n}"));
        return arrayList2;
    }

    private final List<Brick> setupPocketensorCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PtSetTrainingBrick(1));
        arrayList.add(new PtCreateTensorBrick("tensor", "10,4", 0.0f, true));
        arrayList.add(new PtOpBrick());
        arrayList.add(new PtBackwardBrick("loss"));
        arrayList.add(new PtStepBrick(0.01f));
        arrayList.add(new MLStepAdamBrick(new Formula(Float.valueOf(0.01f))));
        arrayList.add(new PtSetTensorBrick("tensor", "1,2,3"));
        arrayList.add(new PtSetByIndexBrick("tensor", 0, 1.0f));
        arrayList.add(new PtReshapeBrick("tensor", "20,2"));
        arrayList.add(new MLSaveBrick("model.bin"));
        arrayList.add(new MLLoadBrick("model.bin"));
        return arrayList;
    }

    private final List<Brick> setupFast2dCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Fast2DCreateBrick("object"));
        arrayList.add(new Fast2DDeleteBrick("object"));
        Double dValueOf = Double.valueOf(100.0d);
        Double dValueOf2 = Double.valueOf(200.0d);
        arrayList.add(new Fast2DSetPositionBrick("object", dValueOf, dValueOf2));
        arrayList.add(new Fast2DSetRotationBrick("object", Double.valueOf(90.0d)));
        arrayList.add(new Fast2DSetScaleBrick("object", dValueOf2, Double.valueOf(15.0d)));
        Double dValueOf3 = Double.valueOf(10.0d);
        Double dValueOf4 = Double.valueOf(5.0d);
        arrayList.add(new Fast2DSetVelocityBrick("object", dValueOf3, dValueOf4));
        arrayList.add(new Fast2DSetAngularVelocityBrick("object", dValueOf3));
        arrayList.add(new Fast2DSetTextureBrick("object", "image.png"));
        Double dValueOf5 = Double.valueOf(255.0d);
        Double dValueOf6 = Double.valueOf(0.0d);
        arrayList.add(new Fast2DSetColorBrick("object", dValueOf5, dValueOf6, dValueOf6, Double.valueOf(120.0d)));
        Double dValueOf7 = Double.valueOf(2.0d);
        arrayList.add(new Fast2DSetCameraBrick(dValueOf, dValueOf2, dValueOf7));
        arrayList.add(new Fast2DMakePhysicsBrick("object", Double.valueOf(1.0d), "CIRCLE", dValueOf3, Double.valueOf(0.7d), Double.valueOf(0.2d)));
        arrayList.add(new Fast2DSetCollisionFilterBrick("object", dValueOf6, Double.valueOf(-1.0d)));
        arrayList.add(new Fast2DApplyForceBrick("object", dValueOf, Double.valueOf(300.0d)));
        arrayList.add(new Fast2DApplyImpulseBrick("object", dValueOf3, dValueOf4));
        arrayList.add(new Fast2DSetPhysicsVelocityBrick("object", dValueOf3, dValueOf7));
        arrayList.add(new Fast2DSetGravityBrick(0.0f, -9.8f));
        return arrayList;
    }

    private final List<Brick> setupFileCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        String string = context.getString(R.string.brick_write_variable_to_file_default_value);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(new WriteVariableToFileBrick(string));
        String string2 = context.getString(R.string.brick_write_variable_to_file_default_value);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        arrayList.add(new ReadVariableFromFileBrick(string2));
        arrayList.add(new WriteToFilesBrick("variable.txt"));
        arrayList.add(new ReadFromFilesBrick("variable.txt"));
        arrayList.add(new DeleteFilesBrick("variable.txt"));
        arrayList.add(new FileUrlBrick("http://e95814zx.beget.tech/map.jpg", "fileFromUrl.jpg"));
        arrayList.add(new FilesUrlBrick("http://e95814zx.beget.tech/map.jpg", "fileFromUrl.jpg"));
        arrayList.add(new ZipProjectFilesBrick("file1.txt, file2.png", "archive.zip"));
        arrayList.add(new UnzipProjectFilesBrick("archive.zip"));
        arrayList.add(new GetZipFileNamesBrick("myZip.zip"));
        arrayList.add(new ZipBrick("myZip.zip", "my_actor.png,fileFromUrl.jpg"));
        arrayList.add(new UnzipBrick("myZip.zip"));
        arrayList.add(new OpenFileBrick("fileFromUrl.txt"));
        arrayList.add(new MoveFilesBrick("variable.txt"));
        arrayList.add(new MoveDownloadsBrick("variable.txt"));
        arrayList.add(new CopyProjectFileBrick("variable.txt", "copy_variable.txt"));
        arrayList.add(new ChooseFileBrick());
        arrayList.add(new ExportProjectFileBrick("file.txt"));
        arrayList.add(new ShareBrick("Привет из NewCatroid!"));
        arrayList.add(new SaveToInternalStorageBrick("file.txt", "myAwesomeApp/file.txt"));
        arrayList.add(new LoadFromInternalStorageBrick("myAwesomeApp/file.txt"));
        arrayList.add(new SaveLookBrick("my_actor.png"));
        arrayList.add(new LookFileBrick("my_actor.png"));
        arrayList.add(new SaveLookFilesBrick("look.png"));
        arrayList.add(new SetLookFilesBrick("look.png"));
        arrayList.add(new SoundFileBrick("my_sound.mp3"));
        arrayList.add(new SoundFilesBrick("my_sound_FROM_PROJECT_FILES.mp3"));
        arrayList.add(new PrepareSoundBrick("my_sound.mp3", "sound"));
        arrayList.add(new StartRecordingBrick());
        arrayList.add(new StopRecordingBrick("audio.mp3"));
        arrayList.add(new LoadNNBrick("model.onnx"));
        arrayList.add(new ResizeImgBrick("image.png", (Integer) 64, (Integer) 64));
        arrayList.add(new GrayscaleImgBrick("image.png"));
        arrayList.add(new NormalizeImgBrick("image.png", "rTable", "gTable", "bTable"));
        arrayList.add(new ApplyShaderToImageBrick("image.png", "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_projTrans;\n\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main() {\n    vec4 original = texture2D(u_texture, v_texCoords);\n    float roughness = original.r;\n    float metallic = 0.0;\n    gl_FragColor = vec4(1.0, roughness, metallic, 1.0);\n}"));
        arrayList.add(new UploadFileBrick(new Formula("https://"), new Formula("file.txt"), 0, new Formula("application/"), 0));
        arrayList.add(new CreateVideoBrick("myVideoPlayer", "video.mp4", (Integer) 0, (Integer) 0, (Integer) 750, (Integer) 500, (Integer) 1, (Integer) 0));
        arrayList.add(new LoadNativeModuleBrick("libz.so"));
        arrayList.add(new LoadPythonLibraryBrick("telebot.whl"));
        arrayList.add(new RunVm2Brick("-kernel \"%PROJECT_FILES%/bzImage\" -initrd \"%PROJECT_FILES%/core.gz\" -append \"console=ttyS0 quiet\""));
        arrayList.add(new RunVMBrick("1024", "2", "myDisk.qcow2", "flash.iso"));
        arrayList.add(new Base64ToFileBrick("", "image.png"));
        arrayList.add(new GenerateKeyBrick("keystore.jks", "123456", PathMap.NAME_alias, "KEBAB_337"));
        arrayList.add(new SignApkBrick("input.apk", "output.apk", "keystore.jks", "123456", PathMap.NAME_alias));
        arrayList.add(new UpdateManifestBrick(new Formula("input.apk"), new Formula("com.example.app"), new Formula("My App"), new Formula((Integer) 1), new Formula("v1.0"), new Formula((Integer) 24), new Formula((Integer) 33), new Formula((Integer) 1), new Formula("INTERNET\nMANAGE_EXTERNAL_STORAGE"), new Formula("")));
        arrayList.add(new ExtractFileBrick("input.apk", "assets/project.zip", "extracted_project.zip"));
        arrayList.add(new AddFileToApkBrick("input.apk", "assets/project.zip", "my_project.zip"));
        arrayList.add(new DeleteFromApkBrick("input.apk", "assets/image.png"));
        return arrayList;
    }

    private final List<Brick> setupThreedCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        CreateCubeBrick createCubeBrick = new CreateCubeBrick();
        String string = context.getString(R.string.subcategory_3d_objects);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        CreateCubeBrick createCubeBrick2 = createCubeBrick;
        arrayList.add(new SubCategoryHeaderBrick(string, createCubeBrick2));
        arrayList.add(new Create3dObjectBrick("myObject", "model.obj"));
        arrayList.add(new CreateCubeBrick("myObject"));
        arrayList.add(new CreateSphereBrick("myObject"));
        arrayList.add(new ThreedCreateCylinderBrick("myObject"));
        arrayList.add(new Replace3DModelBrick("myObject", "weapon.glb"));
        arrayList.add(new Remove3dObjectBrick("myObject"));
        arrayList.add(new RemoveObjectsByPrefixBrick("wall_"));
        arrayList.add(new CloneObjectBrick("myObject", "clonedObject"));
        arrayList.add(new SetSpawnInvisibleBrick("myObject"));
        arrayList.add(new SetActiveBrick("myObject", true));
        arrayList.add(new SetObjectColorBrick("myObject", 1.0d, 0.0d, 0.0d));
        arrayList.add(new SetObjectTextureBrick("myObject", "texture.png"));
        arrayList.add(new BakeByPrefixBrick("wall_", "bakedObject"));
        arrayList.add(new Export3dObjectToGlbBrick(new Formula("myObject"), new Formula("exported_model.glb")));
        String string2 = context.getString(R.string.subcategory_3d_transform);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string2, createCubeBrick2));
        arrayList.add(new Set3dPositionBrick("myObject", 10.0d, -5.0d, 0.0d));
        arrayList.add(new Set3dRotationBrick("myObject", 1.0d, 0.0d, 0.0d));
        arrayList.add(new Set3dScaleBrick("myObject", 2.0d, 1.0d, 1.5d));
        arrayList.add(new ObjectLookAtBrick("myObject", 0.0d, 0.0d, 0.0d));
        arrayList.add(new ObjectLookAtCorrectBrick("myObject", 0.0d, 0.0d, 0.0d));
        arrayList.add(new ThreedAlignNormalBrick("myObject", -1.0d, 0.0d, 0.0d));
        arrayList.add(new Ease3DPropertyBrick(new Formula("myObject"), 0, 0, new Formula((Integer) 0), new Formula((Integer) 10), new Formula(Double.valueOf(0.1d))));
        arrayList.add(new SetParentBrick("child", "parent"));
        arrayList.add(new RemoveParentBrick("child"));
        arrayList.add(new AttachToCameraBrick("myObject"));
        arrayList.add(new DetachFromCameraBrick("myObject"));
        String string3 = context.getString(R.string.subcategory_3d_camera);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string3, createCubeBrick2));
        arrayList.add(new SetCameraPositionBrick(200.0d, 200.0d, 200.0d));
        arrayList.add(new CameraLookAtBrick(0.0d, 0.0d, 0.0d));
        arrayList.add(new SetCameraRotationBrick(0.0d, 180.0d, 0.0d));
        arrayList.add(new RotateCameraByBrick(0.0f, 180.0f, 0.0f));
        arrayList.add(new SetCameraRangeBrick(0.1d, 2500.0d));
        arrayList.add(new CameraSettingsBrick(67.0f, 2.0f, 2.0f));
        arrayList.add(new SetThirdPersonCameraBrick("myObject", 10.0d, 10.0d, -20.0d));
        arrayList.add(new CameraTrackingBrick("myObject", 0, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        arrayList.add(new SetFreeCameraBrick());
        arrayList.add(new CameraTouchControlBrick(1, 1.0f, 0.0f, 0.0f, 50.0f, 50.0f));
        String string4 = context.getString(R.string.subcategory_3d_physics);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string4, createCubeBrick2));
        arrayList.add(new SetPhysicsStateBrick("myObject", 2, 0, 1.0d));
        arrayList.add(new SetRotationLockBrick("myObject", true, false, true));
        arrayList.add(new Set3dGravityBrick(0.0d, -9.81d, 0.0d));
        arrayList.add(new Set3dVelocityBrick("myObject", 0.0d, 25.0d, 0.0d));
        arrayList.add(new Apply3dForceBrick("myObject", 5.0d, 0.0d, 0.0d));
        arrayList.add(new Set3dFrictionBrick("myObject", 0.7d));
        arrayList.add(new SetRestitutionBrick("myObject", 0.5d));
        arrayList.add(new SetCCDBrick("myObject", true));
        arrayList.add(new CastRayBrick("ray", 100.0d, 100.0d, 100.0d, -1.0d, -1.0d, -1.0d));
        arrayList.add(new AttachRaySensorBrick("ray", "myObject", 1.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 100.0f));
        arrayList.add(new CreatePointJointBrick("joint", "objA", "objB"));
        arrayList.add(new ThreedCreateFixedConstraintBrick("joint", "objA", "objB"));
        arrayList.add(new AddHingeBrick());
        arrayList.add(new SetHingeMotorBrick("joint", 45.0d, 10.0d));
        arrayList.add(new RemoveJointBrick("joint"));
        String string5 = context.getString(R.string.subcategory_3d_render);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string5, createCubeBrick2));
        arrayList.add(new SetAmbientLightBrick(0.8d, 0.8d, 0.8d));
        arrayList.add(new SetDirectionalLightBrick(LocalePreferences.FirstDayOfWeek.SUNDAY, 0.8d, 0.8d, 0.8d, -1.0d, -0.8d, -0.2d));
        arrayList.add(new SetSkyColorBrick(0.5d, 0.6d, 1.0d));
        arrayList.add(new SetFogBrick(0.5f, 0.6f, 1.0f, 0.1f));
        arrayList.add(new EnablePbrRenderBrick(1));
        arrayList.add(new SetBackgroundLightBrick(0.3d));
        arrayList.add(new SetSkyboxBrick("skybox.hdr"));
        arrayList.add(new SetPointLightBrick(LocalePreferences.FirstDayOfWeek.SUNDAY, 100.0d, 100.0d, 0.0d, 255, 255, XKeySym.XK_ae, 5.0d, 300.0d));
        arrayList.add(new SetSpotLightBrick(LocalePreferences.FirstDayOfWeek.SUNDAY, 100.0d, 100.0d, 0.0d, 0.3d, -0.4d, 0.2d, 255, 255, XKeySym.XK_ae, 5.0d, 60.0d, 1.0d, 300.0d));
        arrayList.add(new SetDirectionalLight2Brick(0.3d, -0.2d, -0.3d, 5.0d));
        arrayList.add(new ConfigureLightBrick(LocalePreferences.FirstDayOfWeek.SUNDAY, 0, "5.0"));
        arrayList.add(new RemovePbrLightBrick(LocalePreferences.FirstDayOfWeek.SUNDAY));
        arrayList.add(new PromoteLightBrick(LocalePreferences.FirstDayOfWeek.SUNDAY));
        arrayList.add(new SetMaxPointLightsBrick(5));
        arrayList.add(new SetShadowQualityBrick(new Formula((Integer) 100), new Formula((Integer) 2048)));
        String string6 = context.getString(R.string.subcategory_3d_shaders);
        Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string6, createCubeBrick2));
        arrayList.add(new SetShaderCodeBrick("attribute vec3 a_position;\nattribute vec3 a_normal;\nattribute vec2 a_texCoord0;\n\nuniform mat4 u_worldTrans;\nuniform mat4 u_projViewTrans;\n\nvarying vec2 v_texCoord;\n\nvoid main() {\n    v_texCoord = a_texCoord0;\n    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);\n}", "#ifdef GL_ES\n    precision mediump float;\n#endif\n\nvarying vec2 v_texCoord;\n\nuniform sampler2D u_diffuseTexture;\nuniform float u_time;\n\nvoid main() {\n    // 1. Берем базовый цвет из текстуры объекта\n    vec4 baseColor = texture2D(u_diffuseTexture, v_texCoord);\n\n    // 2. Создаем цвет для пульсации (ярко-фиолетовый)\n    vec3 pulseColor = vec3(1.0, 0.0, 1.0);\n\n    // 3. Вычисляем коэффициент пульсации на основе времени\n    // sin(u_time * 5.0) колеблется от -1 до 1. Мы преобразуем это в диапазон от 0 до 1.\n    float pulse = (sin(u_time * 5.0) + 1.0) * 0.5;\n\n    // 4. Смешиваем базовый цвет с цветом пульсации\n    vec3 finalColor = mix(baseColor.rgb, pulseColor, pulse);\n\n    // 5. Устанавливаем итоговый цвет пикселя, сохраняя исходную прозрачность\n    gl_FragColor = vec4(finalColor, baseColor.a);\n}"));
        arrayList.add(new SetScreenShaderBrick("attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\n\nvoid main() {\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}", "#ifdef GL_ES\nprecision highp float;\n#endif\n\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture0;\nuniform float u_time;\n\nconst float DISTORTION = 0.22;   // Сила рыбьего глаза\nconst float ZOOM = 1.12;         // Приближение\nconst float CHROMATIC = 0.007;   // Сила RGB-расщепления по краям\n\nvoid main() {\n    vec2 uv = v_texCoords;\n\n    vec2 cc = uv - vec2(0.5);\n    float dist = dot(cc, cc);\n\n    vec2 distortedUV = vec2(0.5) + cc * (1.0 + DISTORTION * dist) / ZOOM;\n\n    if (distortedUV.x < 0.0 || distortedUV.x > 1.0 || distortedUV.y < 0.0 || distortedUV.y > 1.0) {\n        gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);\n        return;\n    }\n\n    vec2 splitOffset = cc * CHROMATIC * dist * 3.0;\n    float r = texture2D(u_texture0, distortedUV - splitOffset).r;\n    float g = texture2D(u_texture0, distortedUV).g;\n    float b = texture2D(u_texture0, distortedUV + splitOffset).b;\n    vec3 color = vec3(r, g, b);\n\n    color.r *= 1.02;\n    color.g *= 1.04;\n    color.b *= 0.93;\n\n    float scanline = sin(distortedUV.y * 650.0 + u_time * 2.5) * 0.03;\n    color -= vec3(scanline);\n\n    float vignette = smoothstep(0.35, 0.75, dist);\n    color *= (1.0 - vignette * 0.55);\n\n    gl_FragColor = vec4(color, 1.0);\n}"));
        arrayList.add(new SetObjectShaderBrick("myObject", "attribute vec3 a_position;\n\nuniform mat4 u_projViewTrans;\nuniform mat4 u_worldTrans;\nuniform float u_time;\n\nuniform float u_waveSpeed;\nuniform float u_waveHeight;\nuniform float u_waveScale;\n\nvarying vec3 v_worldPos;\nvarying vec3 v_normal;\n\nvoid main() {\n    vec4 worldPos = u_worldTrans * vec4(a_position, 1.0);\n    \n    float waveSpeed = u_waveSpeed > 0.001 ? u_waveSpeed : 1.0;\n    float waveHeight = u_waveHeight > 0.001 ? u_waveHeight : 0.05;\n    float waveScale = u_waveScale > 0.001 ? u_waveScale : 0.2;\n\n    float wave = sin(worldPos.x * waveScale + u_time * waveSpeed) * \n                 cos(worldPos.z * waveScale + u_time * waveSpeed * 0.8) * waveHeight;\n    worldPos.y += wave;\n\n    v_worldPos = worldPos.xyz;\n    v_normal = vec3(0.0, 1.0, 0.0); \n\n    gl_Position = u_projViewTrans * worldPos;\n}", "#ifdef GL_ES\nprecision highp float;\n#endif\n\nvarying vec3 v_worldPos;\nvarying vec3 v_normal;\n\nuniform float u_time;\nuniform vec3 u_customCameraPosition;\n\nuniform vec3 u_waterColor;\nuniform vec3 u_skyColor;\nuniform float u_waveSpeed;\nuniform float u_waveScale;\n\nvoid main() {\n    vec3 waterColor = length(u_waterColor) > 0.001 ? u_waterColor : vec3(0.02, 0.35, 0.45);\n    vec3 skyColor = length(u_skyColor) > 0.001 ? u_skyColor : vec3(0.7, 0.85, 0.95);\n    float waveSpeed = u_waveSpeed > 0.001 ? u_waveSpeed : 1.2;\n    float waveScale = u_waveScale > 0.001 ? u_waveScale : 0.3;\n\n    float speed = u_time * waveSpeed;\n    vec2 waveUV1 = v_worldPos.xz * waveScale + vec2(speed * 0.03, speed * 0.015);\n    vec2 waveUV2 = v_worldPos.xz * waveScale * 1.5 - vec2(speed * 0.01, speed * 0.025);\n\n    float nX = sin(waveUV1.x * 10.0) * cos(waveUV2.y * 12.0) * 0.1;\n    float nZ = cos(waveUV1.y * 8.0) * sin(waveUV2.x * 10.0) * 0.1;\n    vec3 waveNormal = normalize(v_normal + vec3(nX, 0.0, nZ));\n\n    vec3 viewDir = normalize(u_customCameraPosition - v_worldPos);\n    vec3 lightDir = normalize(vec3(0.3, 1.0, 0.4)); \n\n    float fresnel = pow(1.0 - max(0.0, dot(v_normal, viewDir)), 3.0);\n\n    vec3 halfDir = normalize(lightDir + viewDir);\n    float spec = pow(max(0.0, dot(waveNormal, halfDir)), 64.0) * 1.5;\n\n    vec3 finalColor = mix(waterColor, skyColor, fresnel);\n    finalColor += vec3(spec);\n\n    gl_FragColor = vec4(finalColor, 0.9);\n}"));
        arrayList.add(new SetShaderUniformVec3Brick("lightColor", 0.4d, 1.0d, 0.4d));
        arrayList.add(new SetShaderUniformFloatBrick("meaningOfLife", 42.0d));
        arrayList.add(new SetObjectShaderUniformBrick(new Formula("myObject"), new Formula("waveSpeed"), new Formula(Float.valueOf(2.5f)), new Formula(""), new Formula("")));
        Double dValueOf = Double.valueOf(255.0d);
        arrayList.add(new SetMaterialBrick("myObject", dValueOf, Double.valueOf(0.0d), dValueOf, dValueOf, Double.valueOf(100.0d), Double.valueOf(0.0d), "none.png", "none.png", "none.png"));
        arrayList.add(new SetTextureTilingBrick("myObject", 10.0f, 10.0f));
        arrayList.add(new SetEmissiveBrick("myObject", 1.0f, 1.0f, 1.0f, 1.0f, 10.0f, "emissive_texture.png"));
        arrayList.add(new ConfigureMaterialBrick("myObject", 1, "0.8"));
        arrayList.add(new SetAnisotropicFilterBrick("myObject", 2.0d));
        String string7 = context.getString(R.string.subcategory_3d_animation);
        Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string7, createCubeBrick2));
        arrayList.add(new PlayAnimationBrick("myObject", "idle", -1, 1.0d, 0.2d));
        arrayList.add(new SetAnimationSpeedBrick("myObject", 10.0f));
        arrayList.add(new StopAnimationBrick("myObject"));
        arrayList.add(new KeyframeAnimationBrick());
        arrayList.add(new ThreedAttachObjectToBoneBrick("attachedObject", "hand", "Hand_R", 0.0d, 0.0d, 0.0d));
        arrayList.add(new ThreedBindBoneToObjectBrick("Hand_R", "hand", "myObject"));
        String string8 = context.getString(R.string.subcategory_3d_voxels);
        Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string8, createCubeBrick2));
        arrayList.add(new VoxelCreateWorldBrick(new Formula("chunk1"), new Formula((Integer) 16), new Formula((Integer) 16), new Formula((Integer) 16), new Formula((Integer) 16), new Formula((Integer) 0), new Formula((Integer) 0)));
        arrayList.add(new VoxelConfigBrick(SchemaSymbols.ATTVAL_TRUE_1, 0, 0, 0));
        arrayList.add(new VoxelSetTransparentBrick(1, 1));
        arrayList.add(new VoxelSetBlockBrick("chunk1", 0.0d, 0.0d, 0.0d, 1, 0));
        arrayList.add(new VoxelLoadStringBrick(new Formula("chunk1"), new Formula("1$1#0$0&1$1#0$0"), new Formula("$"), new Formula("#"), new Formula(ContainerUtils.FIELD_DELIMITER)));
        arrayList.add(new VoxelBuildBrick(new Formula("chunk1"), new Formula("atlas.png"), new Formula((Integer) 16), new Formula((Integer) 16)));
        arrayList.add(new VoxelDeleteBrick(new Formula("chunk1")));
        String string9 = context.getString(R.string.subcategory_3d_sound);
        Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string9, createCubeBrick2));
        arrayList.add(new PrepareSoundBrick2("sound.mp3", "sound"));
        arrayList.add(new PrepareMusicAs3DSoundBrick("sound.mp3", "sound"));
        arrayList.add(new PlaySoundAtPositionBrick("sound", "soundInstance"));
        arrayList.add(new Set3DSoundMaxDistanceBrick("soundInstance", 250.0f));
        arrayList.add(new SetSoundInstanceVolumeBrick("soundInstance", 60.0f));
        arrayList.add(new SetSoundInstancePitchBrick("soundInstance", 140.0f));
        arrayList.add(new Set3DSoundPositionBrick("soundInstance", (Integer) 10, (Integer) 10, (Integer) 10));
        arrayList.add(new StopSoundBrick2("soundInstance"));
        arrayList.add(new SetGlobalSoundVolumeBrick(60.0d));
        String string10 = context.getString(R.string.subcategory_3d_effects);
        Intrinsics.checkNotNullExpressionValue(string10, "getString(...)");
        arrayList.add(new SubCategoryHeaderBrick(string10, createCubeBrick2));
        arrayList.add(new SetAIBrick("myObject", 1, "followingObject", 2.0f, 1.0f, 10.0f, 1.0f, 1));
        arrayList.add(new LoadSceneBrick("my_level.rscene"));
        arrayList.add(new LoadSceneAdditiveBrick("my_level.rscene"));
        arrayList.add(new SetPostProcessingBrick(1, 4, new Formula((Integer) 2)));
        arrayList.add(new SetPostProcessingNewBrick());
        arrayList.add(new SetRenderResolutionBrick(0.5f, 1));
        arrayList.add(new SetFpsBrick(24));
        arrayList.add(new CreateParticlesBrick("particles"));
        arrayList.add(new SetParticleEmissionBrick("particles", 10.0f));
        arrayList.add(new ConfigureParticlesBrick("particles", 0, "15.0"));
        arrayList.add(new DeleteParticlesBrick("particles"));
        return arrayList;
    }

    private final List<Brick> setupInternetCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StartServerBrick("1234"));
        arrayList.add(new ConnectServerBrick(LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS, "1234"));
        arrayList.add(new ListenServerBrick());
        arrayList.add(new SendServerBrick("Hi"));
        arrayList.add(new StopServerBrick());
        arrayList.add(new WriteBaseBrick("firebase_id", ASTExpr.DEFAULT_MAP_KEY_NAME, "hello"));
        arrayList.add(new ReadBaseBrick("firebase_id", ASTExpr.DEFAULT_MAP_KEY_NAME));
        arrayList.add(new DeleteBaseBrick("firebase_id", ASTExpr.DEFAULT_MAP_KEY_NAME));
        arrayList.add(new UploadFileBrick(new Formula("https://"), new Formula("file.txt"), 0, new Formula("application/"), 0));
        String string = context.getString(R.string.brick_web_request_default_value);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(new WebRequestBrick(string));
        arrayList.add(new PostWebRequestBrick("https://api.calfire.com/v2/texts?limit=50&offset=200", "Content-Type:application/json", "{\nusername=password\n}"));
        arrayList.add(new HttpCreateBrick("my_request", "GET", "https://api.example.com/data"));
        arrayList.add(new HttpConfigBrick("my_request", 0, "User-Agent", "NewCatroidClient"));
        arrayList.add(new HttpBodyBrick("my_request", "{\"text\": \"hello\"}", "application/json"));
        arrayList.add(new HttpAttachFileBrick("my_request", "image.png", "file", "image/png"));
        arrayList.add(new HttpSendBrick("my_request"));
        arrayList.add(new HttpSaveFileBrick("my_request", "downloaded_image.png"));
        arrayList.add(new WhenMqttMessageBrick(new WhenMqttMessageScript(new Formula("room_1"))));
        arrayList.add(new MqttConnectBrick("my_lobby", "broker.emqx.io", MqttClient.DEFAULT_SERVER_PORT));
        arrayList.add(new MqttJoinRoomBrick("my_lobby", "room_1", "my_secret_game_salt"));
        arrayList.add(new MqttPublishBrick("my_lobby", "room_1", "my_secret_game_salt", "player_x: 150, player_y: 340"));
        arrayList.add(new MqttDisconnectBrick("my_lobby"));
        return arrayList;
    }

    private final List<Brick> setupAssertionsCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AssertEqualsBrick());
        arrayList.add(new AssertUserListsBrick());
        arrayList.add(new ParameterizedBrick());
        arrayList.add(new WaitTillIdleBrick());
        arrayList.add(new TapAtBrick(-100, BrickValues.TOUCH_Y_START));
        arrayList.add(new TapForBrick(-100, BrickValues.TOUCH_Y_START, 0.3d));
        arrayList.add(new TouchAndSlideBrick(-100, BrickValues.TOUCH_Y_START, 100, 200, 0.3d));
        arrayList.add(new FinishStageBrick());
        arrayList.add(new StoreCSVIntoUserListBrick((Integer) 1, context.getString(R.string.brick_store_csv_into_userlist_data)));
        String string = context.getString(R.string.brick_web_request_default_value);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(new WebRequestBrick(string));
        return arrayList;
    }

    public final boolean searchList(Brick searchBrick, List<? extends Brick> list) {
        Intrinsics.checkNotNullParameter(searchBrick, "searchBrick");
        Intrinsics.checkNotNullParameter(list, "list");
        List<? extends Brick> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((Brick) it.next(), searchBrick.getClass())) {
                return true;
            }
        }
        return false;
    }

    public final String getBrickCategory(Brick brick, boolean isBackgroundSprite, Context context) {
        String string;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = configuration.locale;
        configuration.locale = Locale.ENGLISH;
        resources.updateConfiguration(configuration, null);
        if (searchList(brick, setupControlCategoryList(context))) {
            string = resources.getString(R.string.category_control);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupEventCategoryList(context, isBackgroundSprite))) {
            string = resources.getString(R.string.category_event);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupMotionCategoryList(context, isBackgroundSprite))) {
            string = resources.getString(R.string.category_motion);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupSoundCategoryList(context))) {
            string = resources.getString(R.string.category_sound);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupLooksCategoryList(context, isBackgroundSprite))) {
            string = resources.getString(R.string.category_looks);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupPenCategoryList(isBackgroundSprite))) {
            string = resources.getString(R.string.category_pen);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupDataCategoryList(context, isBackgroundSprite))) {
            string = resources.getString(R.string.category_data);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupLegoNxtCategoryList())) {
            string = resources.getString(R.string.category_lego_nxt);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupLegoEv3CategoryList())) {
            string = resources.getString(R.string.category_lego_ev3);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupArduinoCategoryList())) {
            string = resources.getString(R.string.category_arduino);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupDroneCategoryList())) {
            string = resources.getString(R.string.category_drone);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupJumpingSumoCategoryList())) {
            string = resources.getString(R.string.category_jumping_sumo);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupPhiroProCategoryList())) {
            string = resources.getString(R.string.category_phiro);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupRaspiCategoryList())) {
            string = resources.getString(R.string.category_raspi);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupChromecastCategoryList(context))) {
            string = resources.getString(R.string.category_cast);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupEmbroideryCategoryList(context))) {
            string = resources.getString(R.string.category_embroidery);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (searchList(brick, setupAssertionsCategoryList(context))) {
            string = resources.getString(R.string.category_assertions);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else {
            string = "No Match";
        }
        if (brick instanceof AskBrick) {
            string = resources.getString(R.string.category_looks);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof AskSpeechBrick) {
            string = resources.getString(R.string.category_sound);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof LookRequestBrick) {
            string = resources.getString(R.string.category_looks);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof BackgroundRequestBrick) {
            string = resources.getString(R.string.category_looks);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof WhenClonedBrick) {
            string = resources.getString(R.string.category_control);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof WhenBackgroundChangesBrick) {
            string = resources.getString(R.string.category_event);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof SetVariableBrick) {
            string = resources.getString(R.string.category_data);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof WebRequestBrick) {
            string = resources.getString(R.string.category_data);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof StoreCSVIntoUserListBrick) {
            string = resources.getString(R.string.category_data);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof UserDefinedBrick) {
            string = resources.getString(R.string.category_user_bricks);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof UserDefinedReceiverBrick) {
            string = resources.getString(R.string.category_user_bricks);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof ParameterizedEndBrick) {
            string = resources.getString(R.string.category_assertions);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        } else if (brick instanceof WriteEmbroideryToFileBrick) {
            string = resources.getString(R.string.category_embroidery);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        }
        configuration.locale = locale;
        resources.updateConfiguration(configuration, null);
        return string;
    }

    public final List<Brick> setupFavoriteBricksCategoryList(Context context, boolean isBackgroundSprite) {
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> favoriteClassNames = FavoriteBricksManager.INSTANCE.getFavoriteClassNames(context);
        if (favoriteClassNames.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        List<Brick> allAvailableBricks = getAllAvailableBricks(context, isBackgroundSprite);
        ArrayList arrayList = new ArrayList();
        for (Object obj : allAvailableBricks) {
            if (favoriteClassNames.contains(((Brick) obj).getClass().getName())) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final List<Brick> getAllAvailableBricks(Context context, boolean isBackgroundSprite) {
        Intrinsics.checkNotNullParameter(context, "context");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(setupEventCategoryList(context, isBackgroundSprite));
        arrayList.addAll(setupControlCategoryList(context));
        arrayList.addAll(setupMotionCategoryList(context, isBackgroundSprite));
        arrayList.addAll(setupSoundCategoryList(context));
        arrayList.addAll(setupLooksCategoryList(context, isBackgroundSprite));
        arrayList.addAll(setupPenCategoryList(isBackgroundSprite));
        arrayList.addAll(setupDataCategoryList(context, isBackgroundSprite));
        arrayList.addAll(setupDeviceCategoryList(context, isBackgroundSprite));
        arrayList.addAll(setupLegoNxtCategoryList());
        arrayList.addAll(setupLegoEv3CategoryList());
        arrayList.addAll(setupDroneCategoryList());
        arrayList.addAll(setupJumpingSumoCategoryList());
        arrayList.addAll(setupPhiroProCategoryList());
        arrayList.addAll(setupChromecastCategoryList(context));
        arrayList.addAll(setupRaspiCategoryList());
        arrayList.addAll(setupEmbroideryCategoryList(context));
        arrayList.addAll(setupPlotCategoryList(context));
        arrayList.addAll(setupNeuralCategoryList(context));
        arrayList.addAll(setupPocketensorCategoryList(context));
        arrayList.addAll(setupFast2dCategoryList(context));
        arrayList.addAll(setupFileCategoryList(context));
        arrayList.addAll(setupThreedCategoryList(context));
        arrayList.addAll(setupInternetCategoryList(context));
        arrayList.addAll(setupAssertionsCategoryList(context));
        arrayList.addAll(setupLibrariesCategoryList());
        HashSet hashSet = new HashSet();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (hashSet.add(((Brick) obj).getClass().getName())) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }
}
