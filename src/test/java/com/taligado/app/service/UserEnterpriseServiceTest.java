package com.taligado.app.service;

import com.taligado.app.model.Role;
import com.taligado.app.model.UserEnterprise;
import com.taligado.app.repository.IRoleRepository;
import com.taligado.app.repository.IUserEnterpriseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserEnterpriseServiceTest {

    @InjectMocks
    private UserEnterpriseService userEnterpriseService;

    @Mock
    private IUserEnterpriseRepository userEnterpriseRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterNewUser_WithRole() {
        // Arrange
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword("senhaSegura123");
        Long idRole = 1L;

        Role role = new Role();
        role.setId(idRole);

        when(roleRepository.findById(idRole)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(userEnterprise.getPassword())).thenReturn("encodedPassword");

        // Act
        userEnterpriseService.registerNewUser(userEnterprise, idRole);

        // Assert
        verify(userEnterpriseRepository, times(1)).save(userEnterprise);
        assertTrue(userEnterprise.getRoles().contains(role));
        assertEquals("encodedPassword", userEnterprise.getPassword());
    }

    @Test
    void testRegisterNewUser_WithoutRole() {
        // Arrange
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setUsername("empresa_teste");
        userEnterprise.setPassword("senhaSegura123");

        Role defaultRole = new Role();
        defaultRole.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(userEnterprise.getPassword())).thenReturn("encodedPassword");

        // Act
        userEnterpriseService.registerNewUser(userEnterprise, null);

        // Assert
        verify(userEnterpriseRepository, times(1)).save(userEnterprise);
        assertTrue(userEnterprise.getRoles().contains(defaultRole));
        assertEquals("encodedPassword", userEnterprise.getPassword());
    }

    @Test
    void testGetUserByUsername() {
        // Arrange
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setUsername("empresa_teste");

        when(userEnterpriseRepository.findByUsername("empresa_teste")).thenReturn(Optional.of(userEnterprise));

        // Act
        Optional<UserEnterprise> result = userEnterpriseService.getUserByUsername("empresa_teste");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("empresa_teste", result.get().getUsername());
    }

    @Test
    void testSaveUserEnterprise() {
        // Arrange
        UserEnterprise userEnterprise = new UserEnterprise();

        // Act
        userEnterpriseService.saveUserEnterprise(userEnterprise);

        // Assert
        verify(userEnterpriseRepository, times(1)).save(userEnterprise);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        UserEnterprise existingUser = new UserEnterprise();
        existingUser.setId(1L);
        existingUser.setUsername("empresa_teste");
        existingUser.setPassword("senhaAntiga");

        UserEnterprise updatedUser = new UserEnterprise();
        updatedUser.setId(1L);
        updatedUser.setUsername("empresa_teste");
        updatedUser.setPassword("senhaNova");

        when(userEnterpriseRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("senhaNova");

        // Act
        userEnterpriseService.updateUser(updatedUser);

        // Assert
        assertEquals("senhaNova", existingUser.getPassword());
        verify(userEnterpriseRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_Failure() {
        // Arrange
        UserEnterprise updatedUser = new UserEnterprise();
        updatedUser.setId(2L);
        updatedUser.setUsername("empresa_teste");

        when(userEnterpriseRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userEnterpriseService.updateUser(updatedUser);
        });
        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setUsername("empresa_teste");

        when(userEnterpriseRepository.findByUsername("empresa_teste")).thenReturn(Optional.of(userEnterprise));

        // Act
        userEnterpriseService.deleteUser("empresa_teste");

        // Assert
        verify(userEnterpriseRepository, times(1)).delete(userEnterprise);
    }

    @Test
    void testDeleteUser_Failure() {
        // Arrange
        when(userEnterpriseRepository.findByUsername("empresa_teste")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userEnterpriseService.deleteUser("empresa_teste");
        });
        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}
