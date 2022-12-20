package chatbot.component;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlotFiller {

	/*
	 * 
	 * [Input] 
	 * One user message (e.g., "What's the weather in State College?")
	 * 
	 * [Output]
	 * A hash table that contains a set of (key, value) tuples, where the "key"
	 * is the name of the slot (e.g., "location") and "value" is the extracted
	 * value (e.g., "State College").
	 * 
	 */
	public Hashtable<String, String> extractSlotValues(String nowInputText) {
		
		//initialize the hash table. You do not need to change this line of code.
		Hashtable<String, String> result = new Hashtable<String, String>();
		
		String[] nowInputWords = nowInputText.trim().toUpperCase().split("[\\s]+");
		
		String[] urgentList = new String[] {"THIS IS URGENT", "HELP URGENTLY", "HELP IMMEDIATELY","NO TIME", "VERY SOON", 
				"ASAP", "AS SOON AS POSSIBLE", "URGENTLY", "RIGHT NOW"};
		for(String urgencyLvl: urgentList) {
			String[] urgencyWords = urgencyLvl.trim().toUpperCase().split("[\\s]+");
			if(findPhrase(urgencyWords, nowInputWords)){
				//adding value to the result hash table
				result.put("Urgency", "HIGH");
			}
		}
		
		String[] cityList = new String[] {"LA", "NEW YORK CITY", "STATE COLLEGE","NYC", "LAS VEGAS"};
		for(String nowCity: cityList) {
			String[] nowCityWords = nowCity.trim().toUpperCase().split("[\\s]+");
			if(findPhrase(nowCityWords, nowInputWords)){
				//adding value to the result hash table
				result.put("Location", nowCity);
			}
		}
		
		String[] foodTypeList = new String[] {"PIZZA", "BURGER", "STEAK", "RAMEN", "PASTA"};
		for(String nowFoodType: foodTypeList) {
			if(nowInputText.toUpperCase().contains(nowFoodType)) {
				//adding value to the result hash table
				result.put("FoodType", nowFoodType);
			}
		}
		
		//To detect urgency 
		String[] nowRelativeList = new String[] {"TODAY", "TOMORROW", "YESTERDAY", "THE NEXT DAY", "TONIGHT", "THIS MORNING",
				"THIS AFTERNOON", "THIS EVENING", "THIS NOON", "NEXT FLIGHT", "UPCOMING FLIGHT", "CURRENT FLIGHT"};
		for(String nowRelativeDate: nowRelativeList) {
			String[] nowDate = nowRelativeDate.trim().toUpperCase().split("[\\s]+");
			if(findPhrase(nowDate, nowInputWords)) {
				//adding value to the result hash table
				result.put("RelativeUrgentTime", nowRelativeDate);
			}
		}
		
		String[] patternArray = {"\\b\\d+\\s(PM|AM)\\b", "\\b\\s\\d+\\s(PM|AM)\\b"
				, "\\b\\d+(PM|AM)\\b", "([01]?[0-9]|2[0-3]):[0-5][0-9]"};		
		
		for(String nowPatternStr: patternArray) {
			Pattern nowPattern = Pattern.compile(nowPatternStr);
			Matcher nowMatcher = nowPattern.matcher(nowInputText.trim().toUpperCase());
			while (nowMatcher.find()) {
				String nowMatchedSubstring = nowMatcher.group();
			    //adding value to the result hash table
				result.put("NewFlightTime", nowMatchedSubstring);
			}
		//return the result hash table. You do not need to change this part of code.
		}
		return result;
		
		/**
		 * Yes/No Confirmation
		 * Tried but does not work
		 * String[] yesList = new String[] {"YES"};
				for(String yesWord: yesList) {
				if(nowInputText.toUpperCase().equals(yesWord)) {
					//adding value to the result hash table
					result.put("Yes", yesWord);
				}
			}
			
			String[] noList = new String[] {"NO"};
			for(String noWord: noList) {
				if(nowInputText.toUpperCase().contains(noWord)) {
					//adding value to the result hash table
					result.put("No", noWord);
				}
			}
		 */
		
	}
	
	private boolean findPhrase(String[] nowCityWords, String[] nowInputWords) {
		
		//iterate through each word in the sentence
		for(int i = 0; i < nowInputWords.length;i++) {
			if(allWordsMatchStartsWith(nowCityWords, nowInputWords, i)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean allWordsMatchStartsWith(String[] nowCityWords, String[] nowInputWords, int index) {
		for(int i = 0; i < nowCityWords.length;i++) {
			//If strings in nowInputWords doesn't match with strings in nowCityWords, function returns false
			if(!nowCityWords[i].equals(nowInputWords[index+i])) {
				return false;
			}
		}
		//if all strings matched, then function returns true
		return true;
	}

}
