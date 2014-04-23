package in.ac.iitb.cse.moodle.app.formats;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ac.iitb.cse.moodle.app.R;
import in.ac.iitb.cse.moodle.app.parser.Question;

public
class QuestionDisplay extends Activity{
	public static ArrayList<Question> questions=new ArrayList<>();

	@Override
	protected
	void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_display);
		if(savedInstanceState==null){
			getFragmentManager().beginTransaction()
			                    .add(R.id.container,new PlaceholderFragment(0))
			                    .commit();
		}
	}

	@Override
	public
	boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_display,menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static
	class PlaceholderFragment extends Fragment{
		int q_no;

		public
		PlaceholderFragment(int i){
			q_no=i;
		}

		@Override
		public
		View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
			int fragment=questions.get(q_no).getRegex().getLayout();
			View rootView=inflater.inflate(fragment,container,false);
			return rootView;
		}
	}
}
