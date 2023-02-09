package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
   
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account getAccount(Account account){
        return accountDAO.getAccount(account.getAccount_id(), account.getUsername(), account.getPassword());
    }

    public Account addAccount(Account account){
        boolean same = this.accountDAO.getAllAccounts().contains(account.getUsername());    
        if(account.getUsername() != null && account.getPassword().length() >= 4 && same == false){
                return accountDAO.insertAccount(account);
            } else{
                return null;
            }
    }
}
