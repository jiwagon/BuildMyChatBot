package chatbot.component;

public class FlightIntentClassifier {
	
	private static String[] intentDictionary;
	
	public FlightIntentClassifier() {
		initializeIntentDictionary();
	}
	
	/**
	 * Create a dictionary of intents (under Weather domain)  
	 */
	private void initializeIntentDictionary() {
		intentDictionary = new String[] {"CancelFlight","ChangeFlight"};
		
		System.out.print("Intents: (");
		for(int i=0;i<intentDictionary.length;i++) {
			System.out.print(intentDictionary[i]);
			if(i!=intentDictionary.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println(")");
	}
	
	/**
	 * Calculate the given meesage's score for each intent. The chatbot will
	 * select the intent with the *highest* score.
	 * 
	 * The initial score of each domain is 0.0.
	 * 
	 * @param nowInputText	An English message sent from the user.
	 * @return				An Double array that contains the score of each
	 * 						intent.
	 */
	private static Double[] calculateIntentScores(String nowInputText) {
		
		//DO NOT change the following 4 lines
		//initiate all the scores to 0.0 
		Double[] scoreArray = new Double[intentDictionary.length];
		for(int i=0;i<scoreArray.length;i++) {
			scoreArray[i] = Double.valueOf(0.0);
		}
	
		
		String[] tokenList1 = nowInputText.trim().toLowerCase().split("\\W");
		
		String[] cancelDictionary = new String[] {"cancel", "flight", "cancellation"};
		for (String cancelKeyword: cancelDictionary) {
			for(int i = 0; i < tokenList1.length; i++) {
				//compare the values of each input keyword and intent keyword 
				if(tokenList1[i].compareTo(cancelKeyword) == 0) { 
					//scoreArray[0] indicates the score for CancelFlight Report intent
				}
			}
		}

		String[] flightDictionary = new String[] {"flight", "time", "latest", "update", "date", "info", "information", 
				"next", "upcoming"};
		for (String flightKeyword: flightDictionary) {
			for(int i = 0; i < tokenList1.length; i++) {
				//compare the values of each input keyword and intent keyword 
				if(tokenList1[i].compareTo(flightKeyword) == 0) { 
					//scoreArray[0] indicates the score for Weather Report intent
					scoreArray[0] = scoreArray[0].doubleValue()+1.0;
				}
			}
		}
		
		String[] changeDictionary = new String[] {"reschedule", "change", "flight", "postpone"};
		String[] tokenList2 = nowInputText.trim().toLowerCase().split("\\W");
		for(String changeKeyword: changeDictionary) {
			for(int j = 0; j < tokenList2.length; j++) {
				if(tokenList2[j].compareTo(changeKeyword) == 0) {
					//scoreArray[1] indicates the score for ChangeFlight intent
				}
			}
		}
		
		//============= Please Modify Here (ends) =============== 
		
		//do not change the following lines
		if(scoreArray.length!=intentDictionary.length) {
			System.err.println("The score array size does not equal to the intent array size.");
			System.exit(1);
		}
		for(Double nowValue: scoreArray) {
			if(nowValue==null) {
				System.err.println("The score array contains null values.");
				System.exit(1);
			}
		}
		return scoreArray;
	}
	
	/**
	 * Input:
	 * 	nowInputText: the message that the user sent to your chatbot
	 * 
	 * Output:
	 * 	the label (intent) name string
	 * 
	 * @param nowInputText	An English message sent from the user.
	 * @return 				The name of the intent.
	 * 
	 */
	public static String getLabel(String nowInputText) {
		Double[] intentScores = calculateIntentScores(nowInputText);
		Double nowMaxScore = null;
		int nowMaxIndex = -1;
		System.out.print("Intent Scores: (");
		for(int i=0;i<intentScores.length;i++){
			System.out.print(intentScores[i].doubleValue());
			if(i!=intentScores.length-1) {
				System.out.print(", ");
			}
			if(nowMaxScore==null||nowMaxIndex==-1||intentScores[i].doubleValue()>nowMaxScore.doubleValue()) {
				nowMaxIndex = i;
				nowMaxScore = intentScores[i].doubleValue();
			}
		}
		System.out.println(")");
		return intentDictionary[nowMaxIndex];
	}

}