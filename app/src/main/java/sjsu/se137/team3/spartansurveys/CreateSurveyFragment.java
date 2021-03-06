package sjsu.se137.team3.spartansurveys;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateSurveyFragment extends Fragment implements View.OnClickListener{

    private RadioButton mPublic, mPrivate = null;
    private EditText mAccessKey = null;
    private EditText mSurveyTitle, mSurveyDescription = null;
    private EditText mQ1, mQ2, mQ3, mQ4, mQ5 = null;
    private Button mSendSurvey = null;

    public CreateSurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_create_survey, container, false);
        // Inflate the layout for this fragment
        instantiateUI(layout);
         final Integer userid = MyProperties.getInstance().userId;
        //setup connection
        final DatabaseManager dbm = new DatabaseManager();
        //if it is a private survey, show the access key.
        mPrivate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mAccessKey.setVisibility(View.VISIBLE);
            }});
        mPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mAccessKey.setVisibility(View.GONE);
            }});
        mSendSurvey.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                       if(mPublic.isChecked()){
                           dbm.addPublicSurvey(userid,mSurveyTitle.getText().toString(),mSurveyDescription.getText().toString(),1,mQ1.getText().toString(),mQ2.getText().toString(),mQ3.getText().toString(),mQ4.getText().toString(),mQ5.getText().toString());
                           Snackbar.make(view, "inserted public survey " + userid, Snackbar.LENGTH_SHORT)
                                   .setAction("Action", null)
                                   .show();

                       }
                    if(mPrivate.isChecked()){

                           dbm.addPrivateSurvey(userid,mSurveyTitle.getText().toString(),mSurveyDescription.getText().toString(),0,mAccessKey.getText().toString(),mQ1.getText().toString(),mQ2.getText().toString(),mQ3.getText().toString(),mQ4.getText().toString(),mQ5.getText().toString());
                           Snackbar.make(view, "inserted private survey"+ userid, Snackbar.LENGTH_SHORT)
                                   .setAction("Action", null)
                                   .show();
                       }
            }});
        //change title
        getActivity().setTitle("Create a Survey");
           return layout;
        }

    private void instantiateUI(View v){
        View layout = v;
        mSurveyTitle = (EditText) layout.findViewById(R.id.survey_title);
        mSurveyDescription = (EditText) layout.findViewById(R.id.survey_description);
        mPublic = (RadioButton) layout.findViewById(R.id.public_radio_button);
        mPrivate = (RadioButton) layout.findViewById(R.id.private_radio_button);
        mQ1 = (EditText) layout.findViewById(R.id.create_question_1);
        mQ2 = (EditText) layout.findViewById(R.id.create_question_2);
        mQ3 = (EditText) layout.findViewById(R.id.create_question_3);
        mQ4 = (EditText) layout.findViewById(R.id.create_question_4);
        mQ5 = (EditText) layout.findViewById(R.id.create_question_5);
        mAccessKey = (EditText) layout.findViewById(R.id.create_access_code);
        mSendSurvey = (Button) layout.findViewById(R.id.create_survey_button);
    }

//need this to use onclick in the onCreateView function.
    @Override
    public void onClick(View v) {

    }
}
