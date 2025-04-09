import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class Client implements ActionListener{
    private JFrame mainFrame;
    private JPanel chatPanel;
    private static JTextArea chatScreen;
    private JLabel backGround , sideGround , funnyLabel , backGroundLabel , enterName , mainText;
    private ImageIcon mainImage , sideImage , chatImage;
    private JTextField messageField , nameField;
    private JButton sendButton , enterChat , submitName , exitChat;
    private JScrollPane scrollingPane;
    private String username;
    private static Socket socket;

    public void startChatApp() throws UnknownHostException, IOException{

        mainFrame = new JFrame("CHATAPP");
        socket = new Socket("0.0.0.0",5000);

        enterChat = new JButton("Start Chatting");
        enterChat.setOpaque(true);
        enterChat.setPreferredSize(new Dimension(300,50));
        enterChat.setBounds(620,350,300,50);
        enterChat.setFocusable(false);
        enterChat.setForeground(Color.white);
        enterChat.setBackground(Color.black);
        enterChat.setFont(new Font("Arial",Font.PLAIN,30));
        enterChat.addActionListener(this);

        ImageIcon mainImage = new ImageIcon("mainimage.png");
        Image tempImage = mainImage.getImage();
        Image resizedImage = tempImage.getScaledInstance(1535, 1080, Image.SCALE_SMOOTH);
        mainImage = new ImageIcon(resizedImage);
        backGroundLabel = new JLabel(mainImage);
        backGroundLabel.setBounds(0,0,1535,1080);

        ImageIcon iconImage = new ImageIcon("icon.png");
        tempImage = iconImage.getImage();
        resizedImage = tempImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        iconImage = new ImageIcon(resizedImage);
        JLabel iconGroundLabel = new JLabel(iconImage);
        iconGroundLabel.setBounds(670,100,200,200);
        mainFrame.setIconImage(iconImage.getImage());

        enterName = new JLabel();
        enterName.setPreferredSize(new Dimension(400,50));
        enterName.setForeground(Color.WHITE);
        enterName.setBackground(Color.black);
        enterName.setBounds(350,500,400,50);
        enterName.setOpaque(false);
        enterName.setFont(new Font("Arial",Font.PLAIN,30));

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(600,50));
        nameField.setCaretColor(new Color(0,0,0,0));
        nameField.setBorder(BorderFactory.createEmptyBorder());
        nameField.setOpaque(false);
        nameField.setBounds(670, 500, 600, 50);
        nameField.setFont(new Font("Arial",Font.PLAIN,30));
        
        submitName = new JButton("Submit");
        submitName.setPreferredSize(new Dimension(200,50));
        submitName.setBounds(660,590,200,50);
        submitName.setOpaque(false);
        submitName.setFocusable(false);
        submitName.setFont(new Font("Arial" , Font.PLAIN , 30));
        submitName.setBackground(new Color(0,0,0,0));
        submitName.setForeground(new Color(0,0,0,0));
        submitName.setBorder(BorderFactory.createEmptyBorder());
        submitName.addActionListener(this);

        mainText = new JLabel("RaymChat: Unleash the Power of Words! 🌍💬🔥");
        mainText.setPreferredSize(new Dimension(800,50));
        mainText.setForeground(Color.black);
        mainText.setBounds(490,30,800,50);
        mainText.setFont(new Font("Segoe UI Emoji",Font.BOLD , 30));
        mainText.setBackground(new Color(0,0,0,0));
        mainText.setBorder(BorderFactory.createEmptyBorder());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1535,1080);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        mainFrame.add(mainText);
        mainFrame.add(enterChat);
        mainFrame.add(iconGroundLabel);
        mainFrame.add(enterName);
        mainFrame.add(nameField);
        mainFrame.add(submitName);
        mainFrame.add(backGroundLabel);
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==enterChat){
            enterName.setText("  Enter Name: ");
            enterName.setOpaque(true);
            nameField.setBackground(Color.black);
            nameField.setForeground(Color.white);
            nameField.setOpaque(true);
            nameField.setCaretColor(Color.white);
            submitName.setBackground(Color.black);
            submitName.setForeground(Color.white);
            submitName.setOpaque(true);
        }
        else if(e.getSource()==submitName){
            
            username = nameField.getText();

            if(!username.equals("")){
                mainFrame.getContentPane().removeAll();
                mainFrame.revalidate();
                mainFrame.repaint();

                mainImage = new ImageIcon("mainimage.png");
                Image img = mainImage.getImage(); 
                Image resizedImg = img.getScaledInstance(1535, 1080, Image.SCALE_SMOOTH);
                mainImage = new ImageIcon(resizedImg);

                sideImage = new ImageIcon("sideimage.png");
                img = sideImage.getImage(); 
                resizedImg = img.getScaledInstance(300, 750, Image.SCALE_SMOOTH);
                sideImage = new ImageIcon(resizedImg);

                chatImage = new ImageIcon("icon.png");
                img = chatImage.getImage();
                resizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                chatImage = new ImageIcon(resizedImg);

                JLabel chatLabel = new JLabel(chatImage);
                chatLabel.setBounds(75,100,200,200);
                chatLabel.setOpaque(true);

                backGround = new JLabel(mainImage);
                backGround.setBounds(0,0,1535,1080);

                sideGround = new JLabel(sideImage);
                sideGround.setBounds(20,20,300,750);

                chatPanel = new JPanel();
                chatPanel.setLayout(null);
                chatPanel.setPreferredSize(new Dimension(1100,750));
                chatPanel.setBounds(320,20,1165,750);
                chatPanel.setOpaque(true);
                chatPanel.setBackground(Color.white);

                messageField = new JTextField();
                messageField.setPreferredSize(new Dimension(1000,100));
                messageField.setBounds(0, 650, 1000, 100);
                messageField.setFont(new Font("Arial" , Font.PLAIN , 50));
                messageField.setCaretColor(Color.black);
                chatPanel.add(messageField);

                funnyLabel = new JLabel("<html><div style='text-align: center;'>Careful... once you send it, there's no going back.</div></html>");
                funnyLabel.setPreferredSize(new Dimension(165,650));
                funnyLabel.setBounds(1000,0,165,650);
                funnyLabel.setBackground(new Color(37,3,133));
                funnyLabel.setOpaque(true);
                funnyLabel.setForeground(Color.white);
                funnyLabel.setFont(new Font("Arial" , Font.ITALIC , 40));
                chatPanel.add(funnyLabel);

                sendButton = new JButton("Send");
                sendButton.setPreferredSize(new Dimension(165,100));
                sendButton.setBounds(1000,650,165,100);
                sendButton.setOpaque(true);
                sendButton.setFont(new Font("Arial" , Font.PLAIN , 50));
                sendButton.setFocusable(false);
                sendButton.addActionListener(this);
                sendButton.setBackground(Color.black);
                sendButton.setForeground(Color.white);
                chatPanel.add(sendButton);

                chatScreen = new JTextArea();
                chatScreen.setBackground(new Color(140,184,255));
                chatScreen.setOpaque(true);
                chatScreen.setFont(new Font("Arial",Font.PLAIN,40));
                chatScreen.setForeground(Color.BLACK); 
                chatScreen.setEditable(false); 
                chatScreen.setLineWrap(true);
                chatScreen.setWrapStyleWord(true);

                scrollingPane = new JScrollPane(chatScreen , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollingPane.setBounds(0, 0, 1000, 650);
                chatPanel.add(scrollingPane);

                exitChat = new JButton("Exit Chat");
                exitChat.setPreferredSize(new Dimension(200,100));
                exitChat.setBounds(75,500,200,100);
                exitChat.setFont(new Font("Arial",Font.PLAIN,40));
                exitChat.setFocusable(false);
                exitChat.setForeground(Color.white);
                exitChat.setBackground(Color.BLACK);
                exitChat.addActionListener(this);

                mainFrame.add(exitChat);
                mainFrame.add(chatLabel);
                mainFrame.add(sideGround);
                mainFrame.add(chatPanel);
                mainFrame.add(backGround);
                mainFrame.setVisible(true);

                chatScreen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "doSomething");
                chatScreen.getActionMap().put("doSomething",new AbstractAction(){
                    public void actionPerformed(ActionEvent e){
                        String message = messageField.getText();
                        messageField.setText("");
                        if(!message.equals(""))
                        {
                            try {
                                sendMessage(message);
                            } catch (IOException e1) {}
                        }   
                    }
                });

                try {
                    BufferedReader readStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    new Thread(()->{
                        String recieved;
                        try {
                            while((recieved = readStream.readLine())!=null){
                                chatScreen.append(recieved+"\n");
                                chatScreen.setCaretPosition(chatScreen.getDocument().getLength());
                            }
                        } catch (IOException e2){}
                    }).start();
                } catch (IOException e1) {}
            }

        } else if(e.getSource()==sendButton){
            String message = messageField.getText();
            messageField.setText("");
            if(!message.equals(""))
            {
                try {
                    sendMessage(message);
                } catch (IOException e1) {}
            }   
        } else if(e.getSource() == exitChat){
            System.exit(0);
        }
    }

    public void sendMessage(String message) throws IOException{
        PrintWriter sendMsg = new PrintWriter(socket.getOutputStream() , true);
        String sendingMessage = username+": "+message;
        sendMsg.println(sendingMessage);
    }
    public static void main(String[] args) throws UnknownHostException, IOException {
        Client screen = new Client();
        screen.startChatApp();
    }
}
