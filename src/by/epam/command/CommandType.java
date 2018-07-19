package by.epam.command;

import by.epam.command.admin.*;
import by.epam.command.hr.ShowHrByName;
import by.epam.command.user.*;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {

        {
            this.command = new LogoutCommand();
        }
    }, SHOW_ALL_REVIEWS {
        {
            this.command = new ShowAllReviewsCommand();
        }
    }, SHOW_UNCONFIRMED_REVIEWS {
        {
            this.command = new ShowUnconfirmedReviewsCommand();
        }
    }, CONFIRM_RATING {
        {
            this.command = new ConfirmRatingCommand();
        }

    }, EMPTY {
        {
            this.command = new LogoutCommand();
        }
    }, SHOW_BY_SSN {
        {
            this.command = new ShowBySSNCommand();
        }
    }, DELETE_RATING {
        {
            this.command = new DeleteRatingCommand();
        }
    }, SHOW_HR_BY_NAME {
        {
            this.command = new ShowHrByName();
        }
    }, ADD_REVIEW {
        {
            this.command = new AddNewReviewCommand();
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
