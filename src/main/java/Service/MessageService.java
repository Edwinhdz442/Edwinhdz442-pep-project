package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesFromAccount(int posted_by){
        return messageDAO.getAllMessagesFromAccount(posted_by);
    }

    public Message getMessage(int message_id){
        return messageDAO.getMessage(message_id);
    }

    public Message addMessage(Message message){
        if(message.getMessage_text() != null && message.getMessage_text().length() < 255){
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }

    public Message updatMessage(String message_text, int message_id, Message message){
        Message m = this.messageDAO.getMessage(message_id);
        if(m == null) return null;

        messageDAO.updateMessage(message_text, message_id, message);
        return this.messageDAO.getMessage(message.getMessage_id());
    }
}