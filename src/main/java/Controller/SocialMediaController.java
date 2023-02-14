package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginAccountHandler);

        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAccount);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else{
            ctx.status(400);
        }
    }

    public void postLoginAccountHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account login = accountService.loginAccount(account.username, account.password);
        if(login != null){
            ctx.json(mapper.writeValueAsString(login));
        } else {
            ctx.status(401);
        }
    }

    private void postNewMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    public void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
       int message_id = Integer.parseInt(ctx.pathParam("message_id"));
       Message message = messageService.getMessage(message_id);
       if(message != null){
            ctx.json(message);
       }
    }
    
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(message_id);
        if(deletedMessage != null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
        } else{
            ctx.body();
        }

    }


    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
       ObjectMapper mapper = new ObjectMapper();
       Message message = mapper.readValue(ctx.body(), Message.class);
       int message_id = Integer.parseInt(ctx.pathParam("message_id"));
       Message updatedMessage = messageService.updateMessage(message_id, message);
       if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
       } else {
            ctx.status(400);
       }
    }

    private void getAllMessagesFromAccount(Context ctx) throws JsonProcessingException{
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromAccount(account_id);
        if(messages != null){
            ctx.json(messages);
        } else {
            ctx.status(400);
        }
    }
}