package in.ac.iitb.cse.moodle.app.parser;

import java.util.ArrayList;

public
class Question{
	String title;
	String text;
	Format format;
	ArrayList<Answer> answers=new ArrayList<>();

	public
	Question(String block){
		String str=block;
		title=findTitle(str);
		if(!title.isEmpty()) str=str.replaceAll("::","").substring(title.length());
		text=findText(str);
		if(title.isEmpty()) title=text;
		str=str.replace(text,"").replace("{","");
		String parts[]=str.split("\\}");
		format=findFormat(str);
		answers=findAnswers(parts[0],format);
		if(parts.length>1) text+=" ___ "+parts[1];

	}

	private
	ArrayList<Answer> findAnswers(String str,Format format){
		ArrayList<Answer> answers=new ArrayList<>();
		switch(format){
			case MULTIPLE_CHOICE:
				String ans="";
				for(String temp=str;!temp.isEmpty();temp=temp.replace(ans,"").trim()){
					ans=findNextAnswer(temp);
					answers.add(new Answer(ans));
				}
				break;
			case MULTIPLE_RIGHT:

				break;
			case TRUE_FALSE:
				break;
			case SHORT_ANSWER:
				break;
			case MATCHING:
				break;
			case MISSING_WORD:
				break;
			case NUMERICAL:
				break;
			case ESSAY:
				break;
			case DESCRIPTION:
				break;
		}
		return answers;
	}

	private
	String findNextAnswer(String temp){
		String par;
		int right=temp.indexOf("=",1);
		int wrong=temp.indexOf("~",1);
		int index=right<wrong?right:wrong;
		if(wrong<0) index=right;
		if(right<0) index=wrong;
		if(index<0) index=temp.length();
		par=temp.substring(0,index);
		return par;
	}

	private
	Format findFormat(String str){
		format=Format.MULTIPLE_CHOICE;
		if(!str.contains("}")) return Format.DESCRIPTION;
		if(str.equalsIgnoreCase("}")) return Format.ESSAY;
		if(str.startsWith("#")) return Format.NUMERICAL;
		if(str.equalsIgnoreCase("true")
		   ||str.equalsIgnoreCase("false")
		   ||str.equalsIgnoreCase("t")
		   ||str.equalsIgnoreCase("f")) return Format.TRUE_FALSE;
		if(str.contains("->")&&str.contains("=")) return Format.MATCHING;
		if(!str.contains("~")) return Format.SHORT_ANSWER;
		if(str.contains("=")&&str.replaceAll("[^=]", "").length()>1) return Format.MULTIPLE_RIGHT;
//		if(str.trim().length()>str.indexOf("}")+1) return Format.MISSING_WORD;
		return format;
	}

	private
	String findText(String str){
		//Log.d("String ",str);
		return str.substring(0,str.indexOf("{"));
	}

	private
	String findTitle(String str){
		String title="";
		if(str.startsWith("::")){
			title=str.split("::")[1];
		}
		return title;
	}

	@Override public
	String toString(){
		return "\n::"+getTitle()+"::"+"\n"+getText()+"\n{"+getAnswers()+"\n}\n";
	}

	public
	String getTitle(){
		return title;
	}

	public
	String getText(){
		return text;
	}

	public
	String getAnswers(){
		StringBuilder sb=new StringBuilder();
		for(Answer ans : answers)
			sb.append(ans);
		return sb.toString();
	}

	public
	Format getFormat(){
		return format;
	}

	public
	enum Format{
		MULTIPLE_CHOICE,
		MULTIPLE_RIGHT,
		TRUE_FALSE,
		SHORT_ANSWER,
		MATCHING,
		MISSING_WORD,
		NUMERICAL,
		ESSAY,
		DESCRIPTION
	}

	public
	class Answer{
		boolean isCorrect;
		String  text;
		String  feedback;
		int     weight;

		public
		Answer(String str){
			String temp=str;
			String[] split;
			isCorrect=temp.startsWith("=");
			temp=temp.replaceFirst("=","").replaceFirst("~","");
			if(temp.startsWith("%")){
				split=temp.split("%");
				weight=Integer.parseInt(split[1]);
				temp=split[2];
			}
			else weight=0;
			split=temp.split("#");
			text=split[0];
			feedback=split.length>1?split[1]:"";
		}

		public
		String getText(){
			return text;
		}

		public
		String getFeedback(){
			return feedback;
		}

		public
		int getWeight(){
			return weight;
		}

		@Override
		public
		String toString(){
			StringBuilder sb=new StringBuilder();
			sb.append("\n\t").append(isCorrect?"=":"~");
			if(weight!=0) sb.append("%").append(weight).append("%");
			sb.append(text);
			if(!feedback.isEmpty()) sb.append("#").append(feedback);
			return sb.toString();
		}
	}
}
