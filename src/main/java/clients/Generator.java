package clients;

import clients.pojo.Changes;
import clients.pojo.Credentials;
import clients.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;

public class Generator {

    public static User getDefault() {
        final String userEmail = RandomStringUtils.randomAlphabetic(7) + "@test.ru";
        final String userPassword = RandomStringUtils.randomAlphabetic(7);

        final String userName = RandomStringUtils.randomAlphabetic(7);

        return new User(userEmail, userPassword, userName);

    }

    public static User getWithoutEmail() {
        final String userEmailE = null;

        final String userPasswordE = RandomStringUtils.randomAlphabetic(7);

        final String userNameE = RandomStringUtils.randomAlphabetic(7);

        return new User(userEmailE, userPasswordE, userNameE);
    }

    public static User getWithoutPassword() {
        final String userEmailP = RandomStringUtils.randomAlphabetic(7) + "@test.ru";

        final String userPasswordP = null;

        final String userNameP = RandomStringUtils.randomAlphabetic(7);

        return new User(userEmailP, userPasswordP, userNameP);
    }

    public static User getWithoutName() {
        final String userEmailN = RandomStringUtils.randomAlphabetic(7) + "@test.ru";

        final String userPasswordN = RandomStringUtils.randomAlphabetic(7);

        final String userNameN = null;


        return new User(userEmailN, userPasswordN, userNameN);
    }


    public static Credentials getLoginWithIncorrectPassword() {
        final String userEmailL = RandomStringUtils.randomAlphabetic(7) + "@test.ru";

        final String userPasswordL = "NAN";


        return new Credentials(userEmailL, userPasswordL);
    }

    public static Credentials getLoginWithIncorrectEmail() {
        final String userEmailPa = "NAN";

        final String userPasswordPa = RandomStringUtils.randomAlphabetic(7);


        return new Credentials(userEmailPa, userPasswordPa);
    }

    public static Changes changeData() {
        final String userEmailChange = "toxxic@mail.ru";
        final String userNameChange = "toxxic1990";

        return new Changes(userEmailChange, userNameChange);
    }
}
