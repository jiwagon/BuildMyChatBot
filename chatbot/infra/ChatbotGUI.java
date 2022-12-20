package chatbot.infra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import chatbot.infra.Chatbot;

public class ChatbotGUI extends JFrame {

	static private Chatbot nowChatbot;
	
	static private JFrame nowGUIFrame;
	
	static private JTextField inputTextBox;
	static private JTextPane chatHistoryPane;
	static private JScrollPane scroll;
	
	static private JButton psuButton;
	
	public ChatbotGUI(Chatbot nowChatbot) {
		
		this.nowChatbot = nowChatbot;
		
		/*
		 * Task 1: Make the interface prettier!
		 * 
		 * As you can see, this graphical interface (GUI) is not pretty. Please
		 * modify the following codes to move the components around or change
		 * their appearances, such as color or size, to make the interface
		 * looks nicer. Please explain what you did in your video.
		 */
		
		//create the frame of chatbot
		nowGUIFrame = new JFrame();
		nowGUIFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		nowGUIFrame.setVisible(true);
		nowGUIFrame.setResizable(false);
		nowGUIFrame.setLayout(null);
		//Ji's edit: adjusted the size
		nowGUIFrame.setSize(520, 525); 
		//Ji's edit: Changed the title to Ji's Chatbot
		nowGUIFrame.setTitle("Ji's "+nowChatbot.getBotName()+"");
		
		//create JTextPane
		chatHistoryPane = new JTextPane();
		nowGUIFrame.add(chatHistoryPane);
		chatHistoryPane.setSize(500, 400); //Ji's edit: Adjusted the size
		chatHistoryPane.setLocation(10, 10); //Ji's edit: Adjusted the location to have more space 
		
		//create JTextField
		inputTextBox = new JTextField();
		nowGUIFrame.add(inputTextBox);
		//Ji's edit: adjusted the size to match with the text pane
		inputTextBox.setSize(450, 70);
		//Ji's edit: adjusted the location 
		inputTextBox.setLocation(7, 418);
		inputTextBox.addActionListener(new InputTextListener(inputTextBox, chatHistoryPane, this));
		
		/* Task 2: Connect the "Send" button to the chatbot and show the
		 * response on the chat pane
		 * 
		 * For now, when the user clicks the "Send" button, the GUI only shows
		 * a message "Send" to the chat pane. Please modify the
		 * actionPerformed() method in the ButtonListener class so that the
		 * code will execute the following three steps each time the button
		 * is clicked:
		 *     (1) Pass the message that the user typed in the input box (i.e.,
		 *         the "user message") to the chatbot and receive its response
		 *         (i.e., the "bot response").
		 *     (2) Display "[USER NAME]: [user message]" in the chat pane.
		 *     (3) Display "[BOT NAME]: [bot response]" in the chat pane.
		 *     
		 * [Hint] You can take a look at the actionPerformed() in the
		 * InputTextListener class.
		 */
		
		//create JButton
		psuButton = new JButton("Send", null);
		nowGUIFrame.add(psuButton);
		
		//Ji's edit: Moved the send button to the right side of the text field
		//Ji's edit: Changed the height and width of the send button
		psuButton.setBounds(461, 419, 52, 69);
		psuButton.addActionListener(new ButtonListener(inputTextBox, chatHistoryPane, this));
			
	}

	public ChatbotGUI() {
		
	}
	
	public Chatbot getChatbot() {
		return nowChatbot;
	}
	
	public static void appendToPane(JTextPane nowPane, String senderName, String message, Color color){
		
		String nowMsg = senderName+": "+message+"\n";
		
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.FontSize, 16);
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = nowPane.getDocument().getLength();
        nowPane.setCaretPosition(len);
        nowPane.setCharacterAttributes(aset, false);
        nowPane.replaceSelection(nowMsg);
        
    }
	
	
}

class ButtonListener implements ActionListener{
	
	private ChatbotGUI chatbotUtil;
	private JTextField nowInputTextBox;
	
	//private JTextField nowInputTextBox;
	private JTextPane nowChatHistoryPane;
	
	public ButtonListener(JTextField inputTextBox, JTextPane chatHistoryPane, ChatbotGUI chatbotUtil) {
		this.chatbotUtil = chatbotUtil;
		nowInputTextBox = inputTextBox;
		nowChatHistoryPane = chatHistoryPane;
		//nowUserName = userName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Ji's edit: added this line to store the text input from text field, 
		// in order to append the message to display in text pane
		String nowInputText = nowInputTextBox.getText();
		//Ji's edit: removed the default display message "send",
		// and use the string stored in nowInputText for display in chat pane
		ChatbotGUI.appendToPane(nowChatHistoryPane, chatbotUtil.getChatbot().getUserName(), nowInputText, Color.BLUE);
		
		//Ji's edit: added a variable that receive a response that can trigger a chatbot's response
		// and added Chatbot to generate a string as a response
		String nowChatbotResponse = chatbotUtil.getChatbot().getResponse(nowInputText);
		ChatbotGUI.appendToPane(nowChatHistoryPane, chatbotUtil.getChatbot().getBotName(), nowChatbotResponse, Color.BLACK);
				
		//Reset text box. You can keep this line of code here. 
		nowInputTextBox.setText("");
		
	}
	
}

// Listen to the Enter key
// Learn this class to fix problem 2 & fix actionPerformed above
class InputTextListener implements ActionListener{
	
	private ChatbotGUI chatbotUtil;
	
	private JTextField nowInputTextBox;
	private JTextPane nowChatHistoryPane;
	
	public InputTextListener(JTextField inputTextBox, JTextPane chatHistoryPane, ChatbotGUI chatbotUtil) {
		this.chatbotUtil = chatbotUtil;
		nowInputTextBox = inputTextBox;
		nowChatHistoryPane = chatHistoryPane;
		//nowUserName = userName;
	}

	@Override
	// Action in place after listened to the instruction
	public void actionPerformed(ActionEvent e) {
		
		String nowInputText = nowInputTextBox.getText();
		// append the response to the display, catch user's input and display in chatroom 
		ChatbotGUI.appendToPane(nowChatHistoryPane, chatbotUtil.getChatbot().getUserName(), nowInputText, Color.BLUE);
		
		// Chatbot generates a string as a response
		String nowChatbotResponse = chatbotUtil.getChatbot().getResponse(nowInputText);
		ChatbotGUI.appendToPane(nowChatHistoryPane, chatbotUtil.getChatbot().getBotName(), nowChatbotResponse, Color.BLACK);
		
		nowInputTextBox.setText("");
		
	}
		
}

