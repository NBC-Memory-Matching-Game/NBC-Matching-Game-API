package com.revature.NBCMMG.services;

import com.revature.NBCMMG.datasources.models.User;
import com.revature.NBCMMG.utils.PasswordUtils;
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
    public void register_throwsException_whenGivenDuplicateUser() {
        User duplicateUser = new User("duplicate", "duplicate", "duplicate", 0, 0);
        User user = new User("duplicate", "duplicate", "duplicate", 0, 0);
        String expectedMessage = "The provided username is already taken!";

        when(mockUserRepository.findUserByUsername(duplicateUser.getUsername())).thenReturn(user);
        when(sut.isUsernameTaken(duplicateUser.getUsername())).thenReturn(true);

        ResourcePersistenceException e = assertThrows(ResourcePersistenceException.class, () -> sut.register(user));
        assertEquals(expectedMessage, e.getMessage());
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

    @Test
    public void login_throwsException_whenUser_providesInvalidCredentials() {
        String username = "username";
        String password = "password";

        when(mockPasswordUtils.generateSecurePassword(password)).thenReturn("encryptedPassword");
        when(mockUserRepository.findUserByUsernameAndPassword(username, "encryptedPassword")).thenReturn(null);

        AuthenticationException e = assertThrows(AuthenticationException.class, () -> sut.login(username, password));

        verify(mockPasswordUtils, times(1)).generateSecurePassword(anyString());
        verify(mockUserRepository, times(1)).findUserByUsernameAndPassword(anyString(), anyString());
    }
}
