package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService{
   private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){  
        if(account.getUsername() != null && !account.getUsername().isBlank() && account.getPassword().length() >= 4){
            return accountDAO.insertAccount(account);
        } else{
            return null;
        }
    }

    public Account loginAccount(String username, String password){
        return accountDAO.loginAccount(username, password);
    }
}
