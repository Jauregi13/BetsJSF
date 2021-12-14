package bean;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domeinua.Event;
import domeinua.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class QuestionsBean {

	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private List<Event> events;
	private Event event;
	private Date data;
	private Vector<Question> questions;
	private String question = "";
	private float min_bet;
	private boolean createQuestionDisable = true;
	private String mezua;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}	

	public  void OnDateSelect(SelectEvent event) {
		facadeBL = FacadeBean.getBusinessLogic();
		this.data = (Date)event.getObject();
		this.events =  facadeBL.getEvents(data);
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(Vector<Event> events) {
		this.events = events;
	}

	public void onEventSelect(SelectEvent event) {
		this.event = (Event)event.getObject();
		this.setQuestions(this.event.getQuestions());

	}

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public String close() {
		this.data = null;
		this.question = null;
		this.event = null;
		this.events = null;
		this.questions = null;
		this.min_bet = 0;
		
		return "close";
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public float getMin_bet() {
		return min_bet;
	}

	public void setMin_bet(float min_bet) {
		this.min_bet = min_bet;
	}
	
	public void saveQuestion() {
		try {
			facadeBL.createQuestion(this.event, this.question, this.min_bet);
			this.mezua ="Galdera ondo sortu da";
		} catch (EventFinished e) {
			System.out.print("Gertaera bukatuta dago");
			//e.printStackTrace();
		} catch (QuestionAlreadyExist e) {
			System.out.print("Galdera existitzen da");
			//e.printStackTrace();
		}
	}

	public boolean isCreateQuestionEnable() {
		return createQuestionDisable;
	}

	public void setCreateQuestionEnable(AjaxBehaviorEvent evento) {
		if(this.data != null && this.event != null && this.events!= null && this.question != null && this.min_bet != 0){
			this.createQuestionDisable = false;
		}
		else {
			this.createQuestionDisable = true;
		}
		
		
	}
}
