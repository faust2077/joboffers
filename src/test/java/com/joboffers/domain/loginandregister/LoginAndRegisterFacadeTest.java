package com.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;

class LoginAndRegisterFacadeTest {
    @Test
    void testLogging_whenUserWithGivenNameFound_thenSuccess() {}

    @Test
    void testLogging_whenExistingUserWithGivenNameNotFound_thenFail() {}

    @Test
    void testLogging_whenUserWithGivenNameNotExists_thenFail() {}

    @Test
    void testLogging_whenGivenBadUsernameFormat_thenFail() {}

    @Test
    void testRegistering_whenUserWithGivenNameAlreadyExists_thenFail() {}

    @Test
    void testRegistering_whenUserWithGivenEmailAlreadyExists_thenFail() {}

    @Test
    void testRegistering_whenGivenBadUsernameFormat_thenFail() {}
    @Test
    void testRegistering_whenGivenBadEmailFormat_thenFail() {}

    @Test
    void testRegistering_whenGivenEmailDoesNotExist_thenFail() {}

    @Test
    void testRegistering_whenUserWithGivenNameNotFound_thenSuccess() {}

    @Test
    void testRegistering_whenUserWithGivenEmailNotFound_thenSuccess() {}

}