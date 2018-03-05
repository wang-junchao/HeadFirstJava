package QuizCard;
import java.io.*;
public class QuizCard {
	String question;
	String answer;
	public QuizCard(String a,String b){
		question=a;
		answer=b;
	}
	public String getAnswer()
	{
		return answer;
	}
	public String getQuestion()
	{
		return question;
	}
}
