package hk.collaction.freehkkai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import hk.collaction.freehkkai.helper.UtilHelper;

public class C extends UtilHelper {
	public static final String PREF_FONT_VERSION = "pref_font_version";
	public static final String PREF_FONT_VERSION_ALERT = "pref_font_version_alert";

	public static void openErrorPermissionDialog(Context mContext) {
		MaterialDialog.Builder dialog = new MaterialDialog.Builder(mContext)
				.customView(R.layout.dialog_permission, true)
				.cancelable(false)
				.negativeText(R.string.ui_cancel)
				.positiveText(R.string.dialog_permission_denied_posbtn)
				.onNegative(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
						((Activity) dialog.getContext()).finish();
					}
				})
				.onPositive(new MaterialDialog.SingleButtonCallback() {
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
						Intent intent = new Intent();
						intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
						intent.addCategory(Intent.CATEGORY_DEFAULT);
						intent.setData(Uri.parse("package:" + dialog.getContext().getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						dialog.getContext().startActivity(intent);
						((Activity) dialog.getContext()).finish();
					}
				});
		dialog.show();
	}

	public static String getCurrentFontName(Context mContext) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
		String fontPath = settings.getString(C.PREF_FONT_VERSION, "fonts/freehkkai_4700.ttf");

		return getCurrentFontName(mContext, fontPath);
	}

	public static String getCurrentFontName(Context mContext, @Nullable String fontPath) {
		String[] fontVersionArray = mContext.getResources().getStringArray(R.array.font_version_array);
		String fontName = fontVersionArray[0];
		if (fontPath != null) {
			switch (fontPath) {
				case "fonts/freehkkai_extended.ttf":
					fontName = fontVersionArray[1];
					break;
			}
		}

		return fontName;
	}
}
