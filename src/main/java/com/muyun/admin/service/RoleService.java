package com.muyun.admin.service;

import com.muyun.admin.dto.RoleDTO;
import com.muyun.admin.entity.Role;
import com.muyun.admin.exception.DataNotFoundException;
import com.muyun.admin.mapper.RoleMapper;
import com.muyun.admin.repository.base.RoleRepository;
import com.muyun.admin.vo.RoleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/5/26
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private static final ExampleMatcher MATCHER = ExampleMatcher.matching()
            .withMatcher("name", match -> match.contains().ignoreCase());

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public Page<Role> list(String name, Pageable pageable) {
        Role role = new Role();
        role.setName(name);
        Example<Role> example = Example.of(role, MATCHER);
        return roleRepository.findAll(example, pageable);
    }

    public Set<Long> getRoleMenus(Long id) {
        return roleRepository.getRoleMenusId(id);
    }

    public List<RoleVO> listAll() {
        return roleRepository.findAllRole();
    }

    public Role save(RoleDTO roleDTO) {
        return roleRepository.save(roleMapper.toRole(roleDTO));
    }

    @Transactional
    public Role update(Long id, RoleDTO roleDTO) {
        return roleRepository.findById(id)
                .map(role -> {
                    roleMapper.updateRole(role, roleDTO);
                    return roleRepository.save(role);
                })
                .orElseThrow(() -> DataNotFoundException.DATA_NOT_FOUND_EXCEPTION);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}


