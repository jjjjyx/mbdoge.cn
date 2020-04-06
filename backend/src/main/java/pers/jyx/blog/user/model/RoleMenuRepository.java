package pers.jyx.blog.user.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author jyx
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenuDO, RoleMenuDO.Pk> {

    /**
     * 获取角色的菜单
     * @Query("select new com.ls.putress.user.model.MenuDO(rm.menu) from #{#entityName} rm where rm.role = ?1")
     *     List<MenuDO> findAllByRole(RoleDO role);
     * @param role
     * @return
     */
    @Query("select rm.menu.id from #{#entityName} rm where rm.role = ?1")
    List<Long> findAllByRole(RoleDO role);

    /**
     * 删除菜单关联关系
     *
     * @param id 菜单id
     */
    @Query("delete from #{#entityName} m where m.menu.id = ?1")
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByMenuId(Long id);


    /**
     * 删除角色关联关系
     *
     * @param id 角色id
     */
    @Query("delete from #{#entityName} m where m.role.id = ?1")
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByRole(Long id);

    /**
     * 根据ids 删除管理关系
     * @param ids id 列表
     */
    void deleteAllByMenuIdIn(Collection<Long> ids);
}
