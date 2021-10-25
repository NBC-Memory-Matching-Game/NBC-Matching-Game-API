package com.revature.NBCMMG.services;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTestSuite {

    UserService sut;

    private PasswordUtils mockPasswordUtils;
    private UserRepository mockUserRepository;

    @BeforeEach
    public void beforeEachTest() {
        mockPasswordUtils = mock(PasswordUtils.class);
        mockUserRepository = mock(UserRepository.class);
        sut = new UserService(mockUserRepository);
    }

    @AfterEach
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void register_returnsSuccessfully_whenGivenValidUser() {
        String username = "valid";
        User expectedResult = new User("valid", "valid", "valid", 0, 0);
        User validUser = new User("valid", "valid", "valid", 0, 0);
        when(mockUserRepository.findUserByUsername(username)).thenReturn(null);
        when(mockUserRepository.save(any())).thenReturn(expectedResult);
        when(mockPasswordUtils.generateSecurePassword(validUser.getPassword())).thenReturn("encryptedPassword");

        User actualResult = sut.register(validUser);

        assertEquals(expectedResult, actualResult);

        verify(mockUserRepository, times(1)).findUserByUsername(anyString());
        verify(mockUserRepository, times(1)).save(any());
        verify(mockPasswordUtils, times(1)).generateSecurePassword(anyString());
    }

    @Test
    public void login_returnsPrincipal_whenUser_providesValidCredentials() {
        String username = "valid";
        String password = "valid";
        User authUser = new User("valid", "encryptedPassword", "valid", 0, 0);
        Principal expectedResult = new Principal(authUser);

        when(mockPasswordUtils.generateSecurePassword(password)).thenReturn("encryptedPassword");
        when(mockUserRepository.findUserByUsernameAndPassword(username, password)).thenReturn(authUser);

        Principal actualResult = sut.login(username, password);

        assertEquals(expectedResult, actualResult);
    }
}
