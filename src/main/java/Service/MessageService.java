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
        return messageDAO.getMessageById(message_id);
    }

    public Message addMessage(Message message){
        if((!message.getMessage_text().isBlank()) && message.getMessage_text().length() < 255){
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }

    public Message updateMessage(int message_id, Message message){
        if((!message.getMessage_text().isBlank()) && message.getMessage_text().length() < 255){
            messageDAO.updateMessage(message_id, message);
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }

    public Message deleteMessage(int message_id){
        if(messageDAO.getMessageById(message_id) != null){
            Message m = messageDAO.getMessageById(message_id);
            messageDAO.deleteMessage(message_id);
            return m;
        }
        return null;
    }
}