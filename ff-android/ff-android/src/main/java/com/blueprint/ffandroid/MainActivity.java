package com.blueprint.ffandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends ActionBarActivity
        implements View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public CharSequence mTitle;
    /** The donation object that is created and updated. */
    public Donation donation;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    String SENDER_ID = "699498087377";

    /** The awesome navigation controller. */
    private ResideMenu resideMenu;
    private ArrayList<ResideMenuItem> menuItems;



    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;

    /** The time for the request to respond. */
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /** The Log that will say we are in the main activity. */
    private final static String TAG = "MainActivity";
    /**Fragment declarations**/
    LocationFragment locationFragment;
    AccountFragment accountFragment;
    FormFragment formFragment;
    Fragment currentFragment;
    CongratulatoryFragment congratulatoryFragment;
    DonationListFragment donationListFragment;
    FAQFragment faqFragment;
    DonationDetailFragment donationDetailFragment;
    /** Font Declaration **/
    public Typeface myTypeface;


    /** The logged in user's access token for authentication. */
    public String accessToken;
    public static final String PREFS = "LOGIN_PREFS";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/proxima_nova_regular.otf");
        initializeFragments();
        setContentView(R.layout.activity_donate);

        initializeNavigation();

        context = this;

        replaceFragment(locationFragment);
        mTitle = getTitle();

        donation = new Donation();

        SharedPreferences prefs = getSharedPreferences(PREFS, 0);
        accessToken = prefs.getString("token", "NONE");
        // Check device for Play Services APK.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);
            Log.e(TAG, "REGISTRATION ID:" + regid+ "swag");
            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

    }

    private void initializeNavigation() {
        resideMenu = new ResideMenu(this);
        resideMenu.attachToActivity(this);
        resideMenu.setShadowVisible(false);
        resideMenu.setDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion >= Build.VERSION_CODES.JELLY_BEAN) {
            resideMenu.setBackground(R.drawable.blueberries3);
        } else {
            resideMenu.setBackground(R.drawable.blueberries3_low);
        }

        String titles[] = { "Donate!", "Donation List", "Account", "About", "Logout" };
        int icon[] = { R.drawable.donate, R.drawable.donatelist, R.drawable.account, R.drawable.faq, R.drawable.logout };
        menuItems = new ArrayList<ResideMenuItem>();
        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
            menuItems.add(item);
        }

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return resideMenu.onInterceptTouchEvent(event) || super.dispatchTouchEvent(event);
    }

    private void initializeFragments(){
        locationFragment = LocationFragment.newInstance();
        accountFragment = AccountFragment.newInstance();
        formFragment = FormFragment.newInstance();
        congratulatoryFragment = CongratulatoryFragment.newInstance();
        donationListFragment = DonationListFragment.newInstance();
        faqFragment = FAQFragment.newInstance();
        donationDetailFragment = DonationDetailFragment.newInstance();
        currentFragment = locationFragment;
    }

    @Override
    public void onClick(View view) {
        // update the main content by replacing fragments
        int position = menuItems.indexOf(view);
        setActionBar(position);
        switch (position) {
            case 0:
                replaceFragment(locationFragment);
                break;
            case 1:
                replaceFragment(donationListFragment);
                break;
            case 2:
                replaceFragment(accountFragment);
                break;
            case 3:
                replaceFragment(faqFragment);
                break;
            case 4:
                SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, LoginActivity.class);
                this.finish();
                startActivity(intent);
                break;
        }
        resideMenu.closeMenu();
    }

    private void setActionBar(int position){
        switch (position) {
            case 0:
                mTitle = "Where Is The Pickup?";
                break;
            case 1:
                mTitle = "Donation List";
                break;
            case 2:
                mTitle = "Account Details";
                break;
            case 3:
                mTitle = "About Us";
                break;
            case 4:
                mTitle = "Logout";
                break;


        }
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setTypeface(myTypeface);
        titleView.setText(mTitle);
    }

    public void replaceFragment(Fragment newFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        if(!newFragment.isAdded()){
            ft.add(R.id.container, newFragment);
        }
        ft.hide(currentFragment);
        ft.show(newFragment);
        ft.commit();
        currentFragment = newFragment;
        if (((FragmentLifeCycle) currentFragment).isCreated()) {
            ((FragmentLifeCycle) currentFragment).willAppear();
        }
        if (currentFragment.equals(donationDetailFragment) || currentFragment.equals(formFragment)) {
            Button actionView = (Button) findViewById(R.id.title_bar_left_menu);
            actionView.setBackgroundResource(R.drawable.back);
            if (currentFragment.equals(donationDetailFragment)) {
                findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        replaceFragment(donationListFragment);
                    }
                });
            } else {
                findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        replaceFragment(locationFragment);
                    }
                });
            }
        } else {
            Button actionView = (Button) findViewById(R.id.title_bar_left_menu);
            actionView.setBackgroundResource(R.drawable.titlebar_menu_selector);
            findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }
    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
        }.execute(null, null, null);
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        Log.e(TAG, "REGISTRATION ID:" + regId+ "swag");
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }

    /** Updates the DetailView for Donation D. */
    void updateDetailView(Donation d){
        this.donationDetailFragment.updateView(d);
        FragmentManager fm = getSupportFragmentManager();
        replaceFragment(donationDetailFragment);
    }

    /** Activates when MenuItem Item is selected. If it is in a Detail View
     * of a donation the activity bar turns into a back button. */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (donationDetailFragment.isVisible()) {
            replaceFragment(donationListFragment);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Listens for back button click and opens nav drawer if it's not open
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            FragmentManager fm = getSupportFragmentManager();
            if (!resideMenu.isOpen()) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                return true;
            } else if (resideMenu.isOpen()) {
                resideMenu.closeMenu();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
