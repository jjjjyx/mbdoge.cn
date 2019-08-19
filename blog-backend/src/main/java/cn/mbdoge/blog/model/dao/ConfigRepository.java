package cn.mbdoge.blog.model.dao;

import cn.mbdoge.blog.model.entities.ConfigEntity;
import cn.mbdoge.blog.model.pojo.ConfigKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author jyx
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, String> {
    /**
     * 是否开启注册
     * @return true 开启， false 未开始
     */
    default boolean isEnableRegister() {
        Optional<ConfigEntity> optional = this.findById(ConfigKey.ENABLE_REGISTER);
        return  optional
                .flatMap((configEntity) -> Optional.of(Boolean.TRUE.toString().equals(configEntity.getValue())))
                .orElse(false);
    }

    /**
     * 是否开启管理页面
     * @return true 开启， false 不开启
     */
    default boolean isEnableAdminPage() {
        Optional<ConfigEntity> optional = this.findById(ConfigKey.ENABLE_ADMIN_PAGE);

        return optional
                .flatMap((configEntity) -> Optional.of(Boolean.TRUE.toString().equals(configEntity.getValue())))
                .orElse(false);
    }
    /**
     * 是否开启更新用户功能
     * @return true 开启， false 不开启
     */
    default boolean isEnableUpdateUser() {
        Optional<ConfigEntity> optional = this.findById(ConfigKey.ENABLE_UPDATE_FUN);

        return optional
                .flatMap((configEntity) -> Optional.of(Boolean.TRUE.toString().equals(configEntity.getValue())))
                .orElse(false);
    }

    /**
     * 是否开启批量任务页面
     * @return true 开启， false 不开启
     */
    default boolean isEnableBatchPage() {
        Optional<ConfigEntity> optional = this.findById(ConfigKey.ENABLE_BATCH_PAGE);

        return optional
                .flatMap((configEntity) -> Optional.of(Boolean.TRUE.toString().equals(configEntity.getValue())))
                .orElse(false);
    }

    /**
     * 非正式用户的每日搜索次数
     * @return 次数
     */
    default int testAccountSearchTimes() {
        return this.findById(ConfigKey.TEST_ACCOUNT_SEARCH_LIMIT)
                .flatMap((configEntity) -> {
                    if (configEntity.getValue() == null) {
                        return Optional.empty();
                    }
                    try {
                        Integer integer = Integer.parseInt(configEntity.getValue());
                        return Optional.of(integer);
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                })
                .orElse(10);
    }

    /**
     * 保存配置
     * @param key key
     * @param value value
     */
    @Query(value = "update #{#entityName} a set a.value = ?2 where a.name = ?1")
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void save(String key, String value);
}
