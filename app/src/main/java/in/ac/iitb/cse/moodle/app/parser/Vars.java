package in.ac.iitb.cse.moodle.app.parser;

import android.util.Log;

import java.util.regex.Pattern;

import in.ac.iitb.cse.moodle.app.R;

public
class Vars{
	public
	enum Regex{
		DESCRIPTION("[^}]*",R.layout.fragment_description),
		ESSAY("[}]",R.layout.fragment_essay),
		NUMERICAL("#.*",R.layout.fragment_numerical),
		TRUE_FALSE("[tT]rue|[fF]alse|TRUE|FALSE|[tT]|[fF]",R.layout.fragment_true_false),
		MATCHING("(.*=.+(->).+)+?",R.layout.fragment_matching),
		SHORT_ANSWER("[^~]*",R.layout.fragment_short_answer),
		MISSING_WORD("[.]*[}][.]+",R.layout.fragment_missing_word),
		MULTIPLE_RIGHT("^(?=.*~)(?!.*=).*",R.layout.fragment_multiple_right),
		MULTIPLE_CHOICE("^(?=.*~)(?=.*=).+",R.layout.fragment_multiple_choice);
		String regex;
		int    layout;

		Regex(String regularExpression,int fragment){
			regex=regularExpression;
			layout=fragment;
		}

		public
		boolean matches(final String text){
			Log.d("name",name());
			return Pattern.compile(regex).matcher(text).matches();
		}

		public
		int getLayout(){
			return layout;
		}
	}
}
