package in.ac.iitb.cse.moodle.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import in.ac.iitb.cse.moodle.app.formats.QuestionDisplay;
import in.ac.iitb.cse.moodle.app.parser.Question;

public
class MainActivity extends Activity implements TextToSpeech.OnInitListener{
	static TextToSpeech tts;

	@Override
	protected
	void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(savedInstanceState==null){
			getFragmentManager().beginTransaction()
			                    .add(R.id.container,new PlaceholderFragment())
			                    .commit();
		}
	}

	@Override
	protected
	void onDestroy(){
		super.onDestroy();
	}

	@Override
	public
	boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main,menu);
		return true;
	}

	@Override
	public
	boolean onOptionsItemSelected(MenuItem item){
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id=item.getItemId();
		if(id==R.id.action_settings){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected
	void onActivityResult(int requestCode,int resultCode,Intent data){
		if(requestCode==0){
			if(resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
				tts=new TextToSpeech(this,this);
			}
			else{
				Intent installTTSIntent=new Intent();
				installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}

	@Override
	public
	void onInit(int initStatus){
		if(initStatus==TextToSpeech.SUCCESS){
			tts.setLanguage(Locale.US);
		}
		else Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
	}

	public static
	class PlaceholderFragment extends Fragment{
		public
		PlaceholderFragment(){
		}

		@Override
		public
		View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
			View rootView=inflater.inflate(R.layout.fragment_main,container,false);
			ArrayList<String> questions=getQuestions("sample"+".gift");
			((TextView)rootView.findViewById(R.id.textView)).setText(makeFileIoText(questions));
			Button start=(Button)rootView.findViewById(R.id.start);
			start.setOnClickListener(new View.OnClickListener(){
				@Override
				public
				void onClick(View v){
					Intent intent=new Intent(getActivity(),QuestionDisplay.class);
					startActivity(intent);
				}
			});
			return rootView;
		}

		private
		ArrayList<String> getQuestions(String file){
			ArrayList<String> questions=new ArrayList<>();
			String allQ=readAllQuestions(file);
			String[] strings=allQ.split(System.getProperty("line.separator"));
			for(String str : strings)
				if(!str.isEmpty()) questions.add(str);
			return questions;
		}

		private
		String readAllQuestions(String file){
			return "Who was Einstein?"
			       +"{=Scientist#You are awesome ~%25%Mad fellow#Not really ~Cricketer}This is "
			       +
			       "extra\n\n"
			       +"::Dogs::Who let the dogs out? "
			       +"{~Driver#No way ~%50%Maid#Which maid? ~%25%Servant#Which servant? "
			       +
			       "=Kanta#Thats right}";
		}

		private
		String makeFileIoText(ArrayList<String> question_strings){
			StringBuilder sb=new StringBuilder();
			for(String str : question_strings){
				Question question=new Question(str);
				sb.append(question);
				QuestionDisplay.questions.add(question);
			}
			return sb.toString();
		}
	}
}
