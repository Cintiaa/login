package PM.login.PM;

import PM.login.model.UserType;
import PM.login.model.User;
import PM.login.DAO.UserDAO;

/**
 *
 * @author andreendo
 */
public class PerformLoginPM {

    String login;
    String password;
    UserDAO userDao;
    int contError = 0;

    public PerformLoginPM() {
        login = "";
        password = "";
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void clear() {
        login = "";
        password = "";
        System.out.println("PM.login.EfetuarLoginPM.clear()");
    }

    public PagePM pressLogin(boolean estouroTent) throws Exception {
        login = login.trim();
        password = password.trim();
        if (login.isEmpty() || password.isEmpty()) {
            throw new Exception("Empty fields");
        }

        User user = userDao.getByName(login);
        if (user == null) {
            throw new Exception("Inexistent username");
        }

        if (!user.getPassword().equals(password)) {
            contError++;
            if(VerificaTentativasDeLogin(estouroTent)==3){
                    throw new Exception("User Block");
            }
            throw new Exception("Wrong password");
            
        }

        PagePM pagePM = null;
        if (user.getType() == UserType.ADMIN) {
            pagePM = new AdminMainPagePM();
        } else {
            pagePM = new NormalUserMainPagePM();
        }

        pagePM.setLoggedUser(user);
        contError = 0;

        return pagePM;
    }

    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public int VerificaTentativasDeLogin(boolean estouroTent) {
       if (estouroTent) {
           return 3;
       } else {
           return contError;
       }
        
    }

}
