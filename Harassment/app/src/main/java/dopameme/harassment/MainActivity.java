package dopameme.harassment;




        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    final int NUM_QUESTIONS =  3;
    char[] correctAnswers = {'b', 'a','a'};
    String[] pictures = {"uglyselfie", "lunch", "grocery"};
    String[] scenarios = {"You spot Janice, a classmate of yours, next to the lockers " +
            "taking a selfie. Her makeup looks very sloppy, and she is wearing the most " +
            "ridiculous hairbow you have ever seen. People are sniggering nearby. Your friends " +
            "dare you to walk up to Janice and tell her that her selfie is going to look bad. You " +
            "personally don’t know Janice well, aside from the fact that people generally dislike her. ",
            "A few days later, you and your best friends are eating together at your usual lunch spot " +
                    "when all of a sudden Janice walks up and joins you. All of your friends promptly get up and leave. ",
            "On Saturday morning, you and your friends bump into Janice at the local grocery store. You two exchange " +
                    "greetings, and she is silent for a bit before suddenly asking, \"Hey, are we friends?\" Your friends " +
                    "burst into laughter and are waiting for your response."
    };
    String[] A_options = {"You go through with the dare.",
            "You remain at the table and attempt to strike up a conversation with Janice.",
    "Yes, I think so…"};
    String[] A_results = {"Janice looks embarrassed, and puts her phone away hastily.",
            "Janice is initially shy, but after discovering that you both enjoy " +
                    "painting, she opens up. She seems noticeably happier by the end of lunch. \n",
            "Your friends are incredulous, but do not challenge you. " +
                    "Janice seems visibly relieved, and mutters “okay” before leaving."};
    String[] B_options = {
            "You make an excuse and walk away.",
            "You follow your friends immediately, leaving Janice alone at the table.",
            "Uhh…"
    };
    String[] B_results = {
            "Nothing is affected. Janice takes the photo and posts on social media",
            "You can feel Janice’s eyes on you as you walk away.",
            "Janice’s face becomes red, and she mumbles, \"I understand,\" before " +
                    "walking quickly out of the store."
    };
    String[] submittedAnswer = {"","",""};
    String [] submittedResults = {"","",""};
    int numCorrect;
    ImageView scene;
    int level;
    Button optionA_text, optionB_text, submit;
    TextView scenario_text;
    char submitted = 'c';
    char correct = 'b';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCorrect = 0;
        scene = (ImageView) findViewById(R.id.scene);

        scenario_text = (TextView) findViewById(R.id.scenario);

        //Set up Buttons
        optionA_text = (Button) findViewById(R.id.A);
        optionB_text = (Button) findViewById(R.id.B);
        submit = (Button) findViewById(R.id.submit);

        level = 0;

        nextQuestion(level);
    }

    void nextQuestion(int i){
        Log.d(TAG, Integer.toString(i));
        String myDrawableName = pictures[i];
        int resID = getResources().getIdentifier(myDrawableName, "drawable", getPackageName());
        scene.setImageResource(resID);
        optionA_text.setText(A_options[i]);
        optionB_text.setText(B_options[i]);
        scenario_text.setText(scenarios[i]);
        correct = correctAnswers[i];
        optionA_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitted = 'a';
            }
        });

        optionB_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitted = 'b';
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitted == correct) {
                    Log.d(TAG, "Correct");
                    numCorrect++;
                } else
                    Log.d(TAG, "Incorrect");
                if(submitted == 'a'){
                    submittedAnswer[level]  = A_options[level];
                    submittedResults[level] = A_results[level];
                } else if(submitted == 'b'){
                    submittedAnswer[level]  = B_options[level];
                    submittedResults[level] = B_results[level];
                }
                level++;
                if(level == NUM_QUESTIONS) {
                    Log.d(TAG, "Level now " + Integer.toString(level));
                    lastScene();
                } else
                    nextQuestion(level);
            }
        });
    }

    void lastScene(){
        Log.d(TAG, "Last Scene");
        boolean success;
        if((double)numCorrect/(double)NUM_QUESTIONS >= 0.8){
            success = true;
        } else{
            success = false;
        }
        //Starting a new Intent
        Intent nextScreen = new Intent(getApplicationContext(), EndActivity.class);

        //Sending data to another Activity
        nextScreen.putExtra("Submitted Answers", submittedAnswer);
        nextScreen.putExtra("Submitted Results", submittedResults);
        nextScreen.putExtra("Success", success);
        nextScreen.putExtra("Number Levels", NUM_QUESTIONS);
        Log.d(TAG, "Go Next");
       startActivity(nextScreen);
    }



}
