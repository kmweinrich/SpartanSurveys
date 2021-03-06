package sjsu.se137.team3.spartansurveys;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{
    private EditText mTitle = null;
    private RadioButton mPrivate = null;
    private RadioButton mPublic = null;
    private EditText mAccessCode = null;
    private Button mSearchSurveys = null;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        //instantiates things like buttons and such
        instantiateLayout(layout);
        //listeners for things like button clicks
        setupListeners();
        //change title
        getActivity().setTitle("Search for Surveys");

        return layout;
    }

    /**
     * setup the listeners for things like button or radiobuttons
     */
    private void setupListeners() {

        mPrivate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mAccessCode.setVisibility(View.VISIBLE);

            }});
        mPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mAccessCode.setVisibility(View.GONE);

            }});
        mSearchSurveys.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                attemptSearch(view);

            }});

    }

    //this follows google's pregenerated code attemptLogin() for login for error checking

    /**
     *Makes sure the fields are proper before searching
     * @param v the search view, declared in oncreateview
     */
    private void attemptSearch(View v) {
        View view = v;
        //reset errors
        mPublic.setError(null);
        mTitle.setError(null);
        mPrivate.setError(null);
        mAccessCode.setError(null);
        //set views and cancel flag
        boolean cancel = false;
        View focusView = null;

        //set variables to check
        String title = mTitle.getText().toString();
        String accessCodeString = mAccessCode.getText().toString();

        //if there is no title written, make an error
        if(TextUtils.isEmpty(title)){
            mTitle.setError("please enter a search term");
            focusView = mTitle;
            cancel = true;
        }
        //private or public MUST be clicked
        if(!mPrivate.isChecked() && !mPublic.isChecked() ){
            mPublic.setError("please select public or private");
            focusView = mPublic;
            cancel = true;
        }
        //if the private is checked but there is no access code written, make an error
        if (mPrivate.isChecked() && TextUtils.isEmpty(accessCodeString)){
            mAccessCode.setError("please use an access code for a private survey");
            cancel = true;
        }

        //now, if there is a cancel and the view isnt null, focus on that part.
        if(cancel){
            if (focusView != null) {
                focusView.requestFocus();
            }
        }else{
            //everything is good to go! search!
            if(mPublic.isChecked()){
                publicSearch(view);
            }else if (mPrivate.isChecked()){
                privateSearch(view);
            }
        }
    }

   public void publicSearch(View v){
        View view = v;
       Fragment frag = new SearchListFragment();
       FragmentManager fm = getFragmentManager();
       FragmentTransaction ft = fm.beginTransaction();
       Bundle bundle = new Bundle();
       bundle.putString("survey",mTitle.getText().toString());
       frag.setArguments(bundle);
       ft.replace(R.id.fragment_container, frag, "Survey Response");
       ft.commit();
    }

    public void privateSearch(View v){
        View view = v;
        DatabaseManager dbm = new DatabaseManager();
        Survey s = dbm.getPrivateSurvey(mTitle.getText().toString(),mAccessCode.getText().toString());
        if(s == null){
            Snackbar.make(view,"wrong input. name and access code must match",Snackbar.LENGTH_SHORT).setAction("Action", null).show();

        }else{
            Fragment frag = new ResponseFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putParcelable("survey",s);
            frag.setArguments(bundle);
            ft.replace(R.id.fragment_container, frag, "Survey Response");
            ft.commit();
        }
    }

    /**
     * starts the next view because the search parameters are valid
     */
    public void search(){

    }

    /**
     * instantiate all UI elements here
     *
     * @param v the fragment's view.
     */
    private void instantiateLayout(View v) {
        View layout = v;
//        mPubPriv = (RadioGroup) layout.findViewById(R.id.search_public_radio_button)
        mTitle = (EditText) layout.findViewById(R.id.survey_search_bar);
        mPrivate = (RadioButton) layout.findViewById(R.id.search_private_radio_button);
        mPublic = (RadioButton) layout.findViewById(R.id.search_public_radio_button);
        mAccessCode = (EditText) layout.findViewById(R.id.search_access_code);
        mSearchSurveys = (Button) layout.findViewById(R.id.search_surveys_button);
    }

    //this is needed to extend onclick listener so we can use it in the instantiation. Idk why we need this override. the switch statements don't work.
    @Override
    public void onClick(View view) {

    }


}
