package chatbot.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class DialogueManager {
	
	private List<String> dialogueStateHistory;
	
	private List<String> domainHistory;
	private List<String> intentHistory;
	private List<Hashtable<String, String>> slotHistory;
	
	public DialogueManager() {
		
		this.dialogueStateHistory = new ArrayList<String>();
		dialogueStateHistory.add("START-STATE");
		
		this.domainHistory = new ArrayList<String>();
		domainHistory.add("StartDomain");
		
		this.intentHistory = new ArrayList<String>();
		intentHistory.add("StartIntent");
		
		this.slotHistory = new ArrayList<Hashtable<String, String>>();
		slotHistory.add(new Hashtable<String, String>());
	}
	
	public String getNextState(String nowDomain, String nowIntent, Hashtable<String, String> extractedSlotValues) {
		
		System.out.println("------------ Dialogue Management Log ---------------------");
		System.out.println("Dialogue State BEFORE Message: "+dialogueStateHistory.get(dialogueStateHistory.size()-1));
		
		// Not the most practical because irl need to store huge amount of conversation data
		//keep track of domain, intent, slot values
		domainHistory.add(nowDomain);
		//System.out.println("Size of domainHistory: "+domainHistory.size());
		
		if(nowIntent==null||nowIntent.length()==0) {
			intentHistory.add("Other");
		}else {
			intentHistory.add(nowIntent);
		}
		//System.out.println("Size of intentHistory: "+intentHistory.size());
		//}
		
		slotHistory.add(extractedSlotValues); //add into a hash table 
		//System.out.println("Size of slotHistory: "+slotHistory.size());
		
		String nowNextStateLabel = calculateNextState(); 
		dialogueStateHistory.add(nowNextStateLabel);
		//System.out.println("Size of dialogueStateHistory: "+dialogueStateHistory.size());
		
		String latestState = dialogueStateHistory.get(dialogueStateHistory.size()-1);
		//System.out.println("Dialogue State AFTER Message: "+latestState);
		
		return latestState;
		
	}

	/*
	 * Task: Use all the information (including dialogueStateHistory, domainHistory,
	 * intentHistory, and slotHistory) to decide the next dialogue state.
	 */
	
	private String calculateNextState() {
		
		String latestState = dialogueStateHistory.get(dialogueStateHistory.size()-1);
		
		String latestIntent = intentHistory.get(intentHistory.size()-1);
		String latestDomain = domainHistory.get(domainHistory.size()-1);
		
		
		String latestNonNullIntent = getLatestNonNullIntent();
		System.out.println("latestNonNullIntent: "+latestNonNullIntent);
				
		switch (latestNonNullIntent) {
		
			case "WeatherReport":
				if(hasSlotValue("Location")) {
					return "ANSWER-WEATHER";
				}else {
					return "ASK-LOCATION";
				}
				//break;
	
			case "Snow":
				if(hasSlotValue("Location")) {
					return "ANSWER-SNOW";
				}else {
					return "ASK-LOCATION";
				}
				//break;
	
			case "Rain":
				if(hasSlotValue("Location")) {
					return "ANSWER-RAIN";
				}else {
					return "ASK-LOCATION";
				}
				//break;
	
			case "Other":
				return "CHIT-CHAT";
		
			case "StartIntent":
				if(dialogueStateHistory.size()>1) {
					return "CHIT-CHAT";
				}
				return "FEELING-GREETING";
				
			// EMOTION DIALOGUE 
			
			case "Happy":
				return "HAPPY-HELP-GREETING";
				
			case "Upset":
				return "UPSET-HELP-GREETING";
				
			case "Angry":
				return "ANGRY-HELP-GREETING";
			
				
			case "OrderFood":
				if (hasSlotValue("FoodType")) {
					return "CONFIRMATION";
				}else {
					return "ASK-FOOD-TYPE-ONFLIGHT";
				}
				
			case "FindFood":
				if(hasSlotValue("Location")) {
					if(hasSlotValue("FoodType")) {
						
						return "ANSWER-FIND-FOOD";
					}else {
						return "ASK-FOOD-TYPE";
					}
				}else {
					return "ASK-LOCATION";
				}
				
			case "CancelFlight":
        		if (hasSlotValue("Urgency") || hasSlotValue("RelativeUrgentTime")) {
        			return "URGENT-CANCELATION";
        		}else {
        			return "USUAL-CANCELATION-ASK";
        		}
        		
			case "ChangeFlight":
        		if (hasSlotValue("Urgency") || hasSlotValue("RelativeUrgentTime")) {
        			if (hasSlotValue("NewFlightTime")) {
        				return "URGENT-CHANGE";
        			}else {
        				return "ASK-NEW-TIME";
        			}
        		}else {
        			return "USUAL-CHANGE-ASK";
        		}
				
				/**
				 * Yes/No Confirmation
				 * case "Confirmation":
					if (hasSlotValue("Yes")) {
						return "YES-CONFIRMED";
					}
					else if (hasSlotValue("No")) {
						return "NO-UNCONFIRMED";
					}
				 */
			
			default:
				System.err.println("Invalid latestNonNullIntent: " + latestNonNullIntent);
				System.exit(1);
		}
		
		return null;
		
	}

	private boolean hasSlotValue(String key) {
		for(Hashtable<String, String> nowSlotValues: slotHistory) {
			if(nowSlotValues.get(key)!=null) {
				if(nowSlotValues.get(key).length()>0) {
					return true;
				}
			}
		}
//		System.out.println("FALSE");
		return false;
	}

	private String getLatestNonNullIntent() {
		
		for(int i=intentHistory.size()-1;i>=0;i--) {
			if(intentHistory.get(i)!=null&&!intentHistory.get(i).equals("Other")) {
				return intentHistory.get(i);
			}
		
		}
		
		return "Other";
	}

	public String executeStateAndGetResponse(String nextState) {
		return DialogueStateTable.execute(nextState, slotHistory);
	}
}
