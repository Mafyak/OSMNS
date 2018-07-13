package by.epam.command;

import by.epam.command.user.AddMyCompany;
import by.epam.command.user.ShowBySSNCommand;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    }, EMPTY {
        {
            this.command = new LogoutCommand();
        }
    }, SHOW_BY_SSN {
        {
            this.command = new ShowBySSNCommand();
        }
    }, ADD_MY_COMPANY {
        {
            this.command = new AddMyCompany();
        }
    }, CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }

    }, REGISTER {
        {
            this.command = new RegisterCommand();
        }
    };
    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
