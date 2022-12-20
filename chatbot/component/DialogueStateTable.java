package chatbot.component;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class DialogueStateTable {

	public DialogueStateTable() {
		
	}
	
	public static String execute(String dialogueStateName, List<Hashtable<String, String>> slotHistory) {
		
		String response = "I am not sure. Could you say more?";
		
		switch (dialogueStateName) {
		
			
			case "CHIT-CHAT":
				response = "[CHIT-CHAT RESPONSE]";
			break;
			
			
			case "FEELING-GREETING":
				response = "Hello! How are you feeling today?";
			break;
			
			
		 	case "HELP-GREETING":
		 		response = "Hello! How can I help you?";
		 	break;
		 	
		 	case "HAPPY-HELP-GREETING":
		 		response = "Awesome! How can I help you?";
		 	break;
		 	
		 	case "STRESS-RATING":
		 		response = "Rate 1(Slightly) - 5(Extremely) how stressful was this issue for you?";
		 	break;
		 	
		 	case "UPSET-HELP-GREETING":
		 		response = "I hope I can better your day, how can I help?";
		 	break;
		 		
		 	case "ANGRY-HELP-GREETING":
		 		response = "I will try my best to help you out, please be patient with me! How can I help?";
		 	break;
		 		
		
			//Dialogue States that are independent from domains/intents 
		
        	case "ASK-LOCATION":
        		response = "Where are you?";
            break;
            	
            //Dialogue States in the Weather domain  
            
        	case "ANSWER-WEATHER":
        		response = "Today's weather forecast [REPORT]";
            break;
            
        	case "ANSWER-SNOW":
        		response = "Today will snow [REPORT]";
            break;
            
        	case "ANSWER-RAIN":
        		response = "Today will rain [REPORT]";
            break;
            
            //Dialogue States in the Food domain
            
        	case "PLACE-ORDER":
        		response = "Order placed, you can pay on the flight. How else can I help?";
        	break;
        	
        	case "ASK-FOOD-TYPE-ONFLIGHT":
        		response = "What do you want to eat? We have Pizza, Burger, Steak, Ramen, and Pasta";
        	break;
        
        	case "ANSWER-FIND-FOOD":
        		response = "You can find food at [ANSWER-FIND-FOOD]";
            break;
            
        	case "ASK-FOOD-TYPE":
        		response = "What do you want to eat?";
            break;
            
        	case "CONFIRMATION":
        		response = "I will place 1 order of your food. Reply Yes to confirm.";
        	break;
        	
        	// Dialogue States in the Flight domain
        	
        	//CancelFlight:
        	
        	case "URGENT-CANCELATION":
        		response = "Your flight will be cancelled shortly after you provided your flight code and email address.";
        	break;
        	
        	case "USUAL-CANCELATION-ASK":
        		response = "Why are you canceling your flight?";
        	break;
            
        	// Change Flight 
        	case "URGENT-CHANGE":
        		response = "I will change your flight to the suggested new time if available.";
        	break;
        	
        	case "USUAL-CHANGE-ASK":
        		response = "Why are you changing your flight?";
        	break;
        	
        	case "ASK-NEW-TIME":
        		response = "What do you want to change your flight time to?";
        	break;
        	
            /**
          	case "ANSWER-ORDER-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;
            
        	case "ASK-LOCATION-ORDER-FOOD":
        		response = "Where do you want to order from?";
            break;
        		
        	case "ASK-LOCATION-FIND-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;
            
            //Yes/No Confirmation
        	case "YES-CONFIRMED":
        		response = "Confirmed. How else can I help you?";
        	break;
        		
        	case "NO-UNCONFIRMED":
        		response = "How else can I help you?";
        	break;
        	*/	
        	
        	default:
        		System.err.println("Invalid dialogueStateName: " + dialogueStateName);
        		System.exit(1);
        		//throw new IllegalArgumentException("Invalid dialogueStateName: " + dialogueStateName);
        		
		}
		return response;
		
	}
	
}
