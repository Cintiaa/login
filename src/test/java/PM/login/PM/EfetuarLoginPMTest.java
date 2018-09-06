package PM.login.PM;

import PM.login.model.UserType;
import PM.login.model.User;
import PM.login.DAO.UserDAO;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author andreendo
 */
public class EfetuarLoginPMTest {

    public EfetuarLoginPMTest() {
    }

    @Test
    public void testClear() {
        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setLogin("andre");
        efetuarLoginPM.setPassword("123");

        efetuarLoginPM.clear();

        assertEquals("", efetuarLoginPM.getLogin());
        assertEquals("", efetuarLoginPM.getPassword());
    }

    @Test
    public void testEmptyFields() {
        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setLogin("");
        efetuarLoginPM.setPassword("");

        try {
            efetuarLoginPM.pressLogin(false);
            fail();
        } catch (Exception e) {
            assertEquals("Empty fields", e.getMessage());
        }
    }

    @Test
    public void testInexistentUsername() {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("andre"))
                .thenReturn(null);

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setUserDao(userDaoMock);
        efetuarLoginPM.setLogin("andre");
        efetuarLoginPM.setPassword("123");

        try {
            efetuarLoginPM.pressLogin(false);
            fail();
        } catch (Exception e) {
            assertEquals("Inexistent username", e.getMessage());
        }
    }

    @Test
    public void testWrongPassword() {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("andre"))
                .thenReturn(new User("andre", "1234", UserType.NORMALUSER));

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setLogin("andre");
        efetuarLoginPM.setPassword("123");

        efetuarLoginPM.setUserDao(userDaoMock);

        try {
            efetuarLoginPM.pressLogin(false);
            fail();
        } catch (Exception e) {
            assertEquals("Wrong password", e.getMessage());
        }
    }

    @Test
    public void testAdminUserLogin() throws Exception {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("admin"))
                .thenReturn(new User("admin", "admin", UserType.ADMIN));

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setLogin("admin");
        efetuarLoginPM.setPassword("admin");

        efetuarLoginPM.setUserDao(userDaoMock);

        PagePM pagePM = efetuarLoginPM.pressLogin(false);
        assertTrue(pagePM instanceof AdminMainPagePM);
        assertEquals("admin", pagePM.getLoggedUser().getUsername());
    }

    @Test
    public void testNormalUserLogin() throws Exception {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("user"))
                .thenReturn(new User("user", "normal", UserType.NORMALUSER));

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setLogin("user");
        efetuarLoginPM.setPassword("normal");

        efetuarLoginPM.setUserDao(userDaoMock);

        PagePM pagePM = efetuarLoginPM.pressLogin(false);
        assertTrue(pagePM instanceof NormalUserMainPagePM);
        assertEquals("user", pagePM.getLoggedUser().getUsername());
    }

    @Test
    public void quantidadeTentativasLogin() {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("cintia"))
                .thenReturn(new User("cintia", "4321", UserType.NORMALUSER));

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setLogin("cintia");
        efetuarLoginPM.setPassword("4311");
        efetuarLoginPM.setUserDao(userDaoMock);

        if (efetuarLoginPM.VerificaTentativasDeLogin(true) == 3) {

            try {
                efetuarLoginPM.pressLogin(true);
                fail("Não deveria chegar aqui");
            } catch (Exception e) {
                assertEquals("User Block", e.getMessage());
            }
        }
    }

    @Test
    public void testDoisErradosUmCerto() {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("cintia"))
                .thenReturn(new User("cintia", "4321", UserType.NORMALUSER));

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setUserDao(userDaoMock);

        for (int i = 0; i < 3; i++) {
            if (i != 2) {
                efetuarLoginPM.setLogin("cintia");
                efetuarLoginPM.setPassword("4311");
            } else {
                efetuarLoginPM.setLogin("cintia");
                efetuarLoginPM.setPassword("4321");
            }
            try {
                efetuarLoginPM.pressLogin(false);
            } catch (Exception e) {

            }
        }
        assertEquals(0, efetuarLoginPM.VerificaTentativasDeLogin(false));
    }
    
    @Test
    public void testDoisErradosUmCertoMaisUmErrado() {
        UserDAO userDaoMock = mock(UserDAO.class);
        when(userDaoMock.getByName("cintia"))
                .thenReturn(new User("cintia", "4321", UserType.NORMALUSER));

        PerformLoginPM efetuarLoginPM = new PerformLoginPM();
        efetuarLoginPM.setUserDao(userDaoMock);

        for (int i = 0; i < 4; i++) {
            if (i != 2) {
                efetuarLoginPM.setLogin("cintia");
                efetuarLoginPM.setPassword("4311");
            } else {
                efetuarLoginPM.setLogin("cintia");
                efetuarLoginPM.setPassword("4321");
            }
            try {
                efetuarLoginPM.pressLogin(false);
            } catch (Exception e) {

            }
        }
        assertEquals(1, efetuarLoginPM.VerificaTentativasDeLogin(false));
    }
}
