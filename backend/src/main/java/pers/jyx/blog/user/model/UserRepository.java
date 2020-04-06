package pers.jyx.blog.user.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDO, Long>, JpaSpecificationExecutor<UserDO> {
    /**
     * 根据用户名称查询用户
     * @param name
     * @return
     */
    Optional<UserDO> findByUsername(String name);
}
