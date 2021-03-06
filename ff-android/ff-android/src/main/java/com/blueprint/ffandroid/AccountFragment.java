package com.blueprint.ffandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 * @author howardchen
 *
 */
public class AccountFragment extends Fragment implements View.OnClickListener, FragmentLifeCycle {

    /** The name of the account.*/
    private String name;
    /** The email of the account.*/
    private String email;
    /** The Organization of the account.*/
    private String organization;
    /** The phone number of the account.*/
    private String phoneNumber;
    /** The String for the Shared Preference. */
    public static final String PREFS = "LOGIN_PREFS";

    private OnFragmentInteractionListener mListener;

    /** The TextView that holds the user's name. */
    private TextView nameText;
    /** The TextView that holds the user's Email. */
    private TextView emailText;
    /** The TextView that holds the user's organization. */
    private TextView organizationText;
    /** The rootview from the parent activity. */
    private View rootView;
    /** The MainActivity. */
    private MainActivity parent;

    private boolean created = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountFragment.
     */
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    /**
     * Basic AccountFragment constructor.
     */
    public AccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS, 0);
        name = prefs.getString("name","");
        email = prefs.getString("email", "");
        organization = prefs.getString("role", "");
        super.onCreate(savedInstanceState);
        System.out.println("Created account fragment!");
        parent = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        created = true;

        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        nameText = (TextView) rootView.findViewById(R.id.account_name);
        nameText.setText(name);
        emailText = (TextView) rootView.findViewById(R.id.account_email);
        emailText.setText(email);
        organizationText = (TextView) rootView.findViewById(R.id.account_organization);
        organizationText.setText(organization);
        setFonts();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            default:
                break;
        }
    }

    /**
     * Sets the fonts of the Buttons and TextViews in this fragment
     */
    private void setFonts(){
        Typeface tf = (parent.myTypeface);
        nameText.setTypeface(tf);
        emailText.setTypeface(tf);
        organizationText.setTypeface(tf);
        ((TextView) rootView.findViewById(R.id.account_name_header)).setTypeface(tf);
        ((TextView) rootView.findViewById(R.id.account_email_header)).setTypeface(tf);
        ((TextView) rootView.findViewById(R.id.account_organization_header)).setTypeface(tf);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void willAppear() {
        return;
    }

    @Override
    public boolean isCreated() {
        return created;
    }

}