package dopameme.harassment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {



    int numLevels;
    boolean success;
    String [] submittedAnswers;
    String [] submittedResults;
    Button continueThis;
    TextView body;
    int level;
    private final String TAG = EndActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start Next" );
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end);
        Log.d(TAG, "Show" );
        Intent i = getIntent();
        numLevels = i.getIntExtra("Number Levels", 0);
        level = 0;
        success = i.getBooleanExtra("Success", false);
        submittedAnswers = i.getStringArrayExtra("Submitted Answers");
        submittedResults = i.getStringArrayExtra("Submitted Results");

        continueThis = (Button)findViewById(R.id.button);
        body = (TextView)findViewById(R.id.textbody);
        String text = "";
        if(success){
            text = "School life resumes as usual. At first, your friends make comments about you and " +
                    "Janice, but eventually it ceases. You and Janice remain on good terms, even " +
                    "if you are not close. You have come to realize that although she has her " +
                    "quirks, she is a kind person.";
        } else{
            text = "You and Janice become distant. One day, the principal draws everyone to an assembly " +
                    "and announces that Janice has switched schools due to her being bullied. The guilt " +
                    "plagues you, because you realize you may have played a part in this without intending to.";
        }
        text += "\nPress 'continue to see how your decisions led to this situation";
        body.setText(text);
        Log.d(TAG, "Show" );
        continueThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastScene(level);
            }
        });

        //finish();

    }

    void lastScene(int i){
        if(i==numLevels){
            body.setText("Hopefully you learned something about how your actions, while apparently insignificant, can have a significant effect" +
                    "on others and even yourself. To learn more, go to http://www.hackharassment.com/");
        }
        if(i > numLevels){

            finish();
        }
        int levelText  = i  + 1;
        String text = "For scenario " + levelText + ", you chose \n";
        text += "\"" + submittedAnswers[i] + "\"\n";
        text += "AS A RESULT...\n";
        text+= submittedResults[i];
        body.setText(text);

        continueThis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                level++;
                lastScene(level);
            }
        });
    }
}
