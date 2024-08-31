package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import github.com.miralhas.lecturizebackend.domain.model.auth.Role;
import github.com.miralhas.lecturizebackend.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findRoleByName(Role.Value.USER)
                .orElseThrow(() -> new BusinessException("Role USER não foi criada!"));
    }


    public Role getAdminRole() {
        return roleRepository.findRoleByName(Role.Value.ADMIN)
                .orElseThrow(() -> new BusinessException("Role ADMIN não foi criada!"));
    }
}
