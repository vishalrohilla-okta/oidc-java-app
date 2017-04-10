package com.okta.sample.service;

import com.okta.sample.model.RegisterModel;
import com.okta.sample.model.RegisterResult;

public interface RegisterService {

    RegisterResult register(RegisterModel model);
}
