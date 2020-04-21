package james.dsp.tile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import james.dsp.activity.DSPManager;

import static james.dsp.service.HeadsetService.getAudioOutputRouting;

public class JamesTile extends TileService {
    private boolean active = false;

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        String mode = getAudioOutputRouting();
        SharedPreferences sharedPrefs = getSharedPreferences(DSPManager.SHARED_PREFERENCES_BASENAME + "." + mode, 0);

        active = sharedPrefs.getBoolean("dsp.masterswitch.enable", false);

        getQsTile().setState(active ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        String mode = getAudioOutputRouting();
        SharedPreferences sharedPrefs = getSharedPreferences(DSPManager.SHARED_PREFERENCES_BASENAME + "." + mode, 0);

        active = sharedPrefs.getBoolean("dsp.masterswitch.enable", false);

        sharedPrefs.edit().putBoolean("dsp.masterswitch.enable", !active).apply();

        getQsTile().setState(active ? Tile.STATE_INACTIVE : Tile.STATE_ACTIVE);
        getQsTile().updateTile();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
