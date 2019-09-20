package com.dmb.recipeapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.dmb.recipeapp.settings.AppConstants;
import com.dmb.recipeapp.utils.RecipesXMLTagConstants;
import com.dmb.recipeapp.utils.SettingsXMLParser;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Used to obtain the XML file either from URL or local assets folder
 * 
 */
public class ObtainApplicationDataActivity extends FragmentActivity {

	private static final String TAG = ObtainApplicationDataActivity.class
			.getSimpleName();
	
	private boolean shouldShowToastMessage;
	
	private InterstitialAd mInterstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		if (AppConstants.ENABLE_INTERSTITIAL_ADMOB) {
			mInterstitialAd = new InterstitialAd(this);
	        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
	        requestNewInterstitial();
	        mInterstitialAd.setAdListener(new AdListener() {
	        	@Override
	        	public void onAdLoaded() {
	        		super.onAdLoaded();
	        		mInterstitialAd.show();
	        	}
			});
	        
		}

		RetrieveXMLData task = new RetrieveXMLData();
		task.execute();
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	/**
	 * Async task to load the XML file input stream to the parser
	 * 
	 */
	class RetrieveXMLData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			AssetManager assetManager = getAssets();
			InputStream inputStream = null;
			try {
				if (RecipesXMLTagConstants.TAG_URL_SETTINGS == null
						|| RecipesXMLTagConstants.TAG_URL_SETTINGS.length() == 0) {
					inputStream = assetManager.open("recipes_settings.xml");

				} else {
					if (isNetworkAvailable()) {
						URL url = new URL(RecipesXMLTagConstants.TAG_URL_SETTINGS);
						inputStream = url.openStream();
					} else {
						shouldShowToastMessage = true;
					}
				}

				if (!shouldShowToastMessage) {
					((ApplicationContext) getApplicationContext())
					.setParsedApplicationSettings(SettingsXMLParser
							.parse(inputStream));
				}
				
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (shouldShowToastMessage) {
				Toast.makeText(ObtainApplicationDataActivity.this,
						getString(R.string.no_internet_connectivity),
						Toast.LENGTH_LONG).show();

				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							ObtainApplicationDataActivity.this.finish();
						} catch (Exception e) {

						}
					}
				}, 3000);

			} 
			
			if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
				mInterstitialAd.show();
			} else {
				startMainActivity();
			}
			
		}

		
	}
	
	private void startMainActivity() {
		if (((ApplicationContext) getApplicationContext()).getParsedApplicationSettings() != null) {
			Intent intent = new Intent(ObtainApplicationDataActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                  //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                  .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
