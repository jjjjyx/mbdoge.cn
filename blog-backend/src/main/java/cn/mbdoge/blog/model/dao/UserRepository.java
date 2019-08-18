package cn.mbdoge.blog.model.dao;

import cn.mbdoge.blog.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * 根据用户名称查询用户
     * @param name
     * @return
     */
    Optional<UserEntity> findByUsername(String name);
}
