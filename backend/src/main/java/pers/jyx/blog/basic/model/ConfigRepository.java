package pers.jyx.blog.basic.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pers.jyx.blog.user.model.UserDetailDO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jyx
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigDO, String> {
    // /**
    //  * 是否开启注册
    //  * @return true 开启， false 未开始
    //  */
    // default boolean isEnableRegister() {
    //     Optional<ConfigDO> optional = this.findById(ConfigKey.ENABLE_REGISTER);
    //     return  optional
    //             .flatMap((ConfigDO) -> Optional.of(Boolean.TRUE.toString().equals(ConfigDO.getValue())))
    //             .orElse(false);
    // }

    /**
     * 根据key 前缀查询
     * @param prefix 前缀
     * @return list
     */
    List<ConfigDO> findAllByNameStartingWith(String prefix);

    /**
     * 根据 key 查询value 默认值 ""
     * @param key key
     * @return value
     */
    default String getValueByKey(String key) {
        Optional<ConfigDO> optional = this.findById(key);
        return  optional
                .flatMap((configDO) -> Optional.of(configDO.getValue()))
                .orElse("");
    }

    /**
     * 创建一个默认的用户详细信息
     * @return user detail
     */
    default UserDetailDO getDefaultUserDetail() {

        List<ConfigDO> configList = this.findAllByNameStartingWith("setting.user.default.");
        Map<String, Object> ret = configList.stream().collect(Collectors.toMap(ConfigDO::getName, ConfigDO::getValue));

        UserDetailDO userDetail = new UserDetailDO();

//        int taskCreatedNumPerDay = Integer.parseInt((String) ret.get(ConfigKey.SETTING_USER_DEFAULT_TASK_CREATED_NUM_PER_DAY));
//        int parallelNum = Integer.parseInt((String) ret.get(ConfigKey.SETTING_USER_DEFAULT_PARALLEL_NUM));
//        int callNumPerMonth = Integer.parseInt((String) ret.get(ConfigKey.SETTING_USER_DEFAULT_CALL_NUM_PER_MONTH));
//        int maxPhoneNum = Integer.parseInt((String) ret.get(ConfigKey.SETTING_USER_DEFAULT_MAX_PHONE_NUM));
//        int maxCallTaskTime = Integer.parseInt((String) ret.get(ConfigKey.SETTING_USER_DEFAULT_MAX_CALL_TASK_TIME));


//        userDetail.setTaskCreatedNumPerDay(taskCreatedNumPerDay);
//        userDetail.setParallelNum(parallelNum);
//        userDetail.setCallNumPerMonth(callNumPerMonth);
//        userDetail.setMaxPhoneNum(maxPhoneNum);
//        userDetail.setMaxCallTaskTime(maxCallTaskTime);

        return userDetail;
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
