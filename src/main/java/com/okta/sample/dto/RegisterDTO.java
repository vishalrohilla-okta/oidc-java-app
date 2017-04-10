package com.okta.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.okta.sample.model.RegisterModel;

public class RegisterDTO {
    public Credentials credentials;
    public Profile profile;

    public RegisterDTO(RegisterModel model) {
        profile = new Profile();
        profile.email = model.getUsername();
        profile.login = model.getUsername();
        profile.firstName = model.getFirstName();
        profile.lastName = model.getLastName();

        credentials = new Credentials();
        credentials.password = new Password();
        credentials.password.value = model.getPassword();
        credentials.recoveryQuestion = new RecoveryQuestion();
        credentials.recoveryQuestion.question = model.getSecurityQuestion();
        credentials.recoveryQuestion.answer = model.getSecurityAnswer();
    }

    class RecoveryQuestion {
        public String question;
        public String answer;
    }

    class Password {
        public String value;
    }

    class Credentials {
        public Password password;
        @JsonProperty("recovery_question")
        public RecoveryQuestion recoveryQuestion;
    }

    class Profile {
        public String firstName;
        public String lastName;
        public String login;
        public String email;
    }
}
