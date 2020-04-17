package pers.jyx.blog.user.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author jyx
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleDO, Long> {

    /**
     * 修改角色的备注
     *
     * @param name   角色key
     * @param remark 新的备注
     */
    @Query(value = "update #{#entityName} e set e.remark = ?2 where e.name = ?1 ")
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void updateRoleRemark(String name, String remark);

    /**
     * 根据角色名获取角色
     *
     * @param name 角色
     * @return
     */
    Optional<RoleDO> findByName(String name);

    /**
     * 根据角色名获取角色列表
     *
     * @param name 角色列表
     * @return
     */
    List<RoleDO> findAllByNameIn(Collection<String> name);
}
