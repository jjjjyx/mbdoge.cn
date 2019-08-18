package cn.mbdoge.blog.model.dao;

import cn.mbdoge.blog.model.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
