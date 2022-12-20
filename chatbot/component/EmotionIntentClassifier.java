package chatbot.component;

public class EmotionIntentClassifier {
	
	private static String[] intentDictionary;
	
	public EmotionIntentClassifier() {
		initializeIntentDictionary();
	}
	
	/**
	 * Create a dictionary of intents (under Emotions domain)  
	 */
	private void initializeIntentDictionary() {
		intentDictionary = new String[]{"Happy", "Upset", "Angry"};
		
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
		
		//initiate all the scores to 0.0 
		Double[] scoreArray = new Double[intentDictionary.length];
		for(int i=0;i<scoreArray.length;i++) {
			scoreArray[i] = Double.valueOf(0.0);
		}
		
		String[] happyDictionary = new String[] {"feeling", "feel", "happy", "awesome", "amazing", 
				"good", "great", "well", "fine"};
		String[] tokenList1 = nowInputText.trim().toLowerCase().split("\\W");
		for (String happyKeyword: happyDictionary) {
			for(int i = 0; i < tokenList1.length; i++) {
				//compare the values of each input keyword and intent keyword 
				if(tokenList1[i].compareTo(happyKeyword) == 0) { 
					//scoreArray[0] indicates the score for happy intent
					scoreArray[0] = scoreArray[0].doubleValue()+1.0;
				}
			
			}
		}
		
		String[] upsetDictionary = new String[] {"feeling", "feel", "meh", "bad", "sad", "unwell", "disappointed", "upset"};
		String[] tokenList2 = nowInputText.trim().toLowerCase().split("\\W");
		for(String upsetKeyword: upsetDictionary) {
			for(int j = 0; j < tokenList2.length; j++) {
				if(tokenList2[j].compareTo(upsetKeyword) == 0) {
					//scoreArray[1] indicates the score for Upset intent
					scoreArray[1] = scoreArray[1].doubleValue()+1.0;
				}
			}
		}
		
		String[] angryDictionary = new String[] {"feeling", "feel", "angry", "impatient", "pissed", "unsatisfied", "annoyed", 
				"no", "time", "frustrated", "irritated"};
		String[] tokenList3 = nowInputText.trim().toLowerCase().split("\\W");
		for(String angryKeyword: angryDictionary) {
			for(String token: tokenList3) {
				if(token.compareTo(angryKeyword) == 0) {
					//scoreArray[2] indicates the score for angry intent
					scoreArray[2] = scoreArray[2].doubleValue()+1.0;
				}
			}
		}
		
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


